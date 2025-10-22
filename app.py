from flask import Flask, render_template, request, redirect, url_for, flash, session
import sqlite3
import os
from datetime import datetime
from werkzeug.security import generate_password_hash, check_password_hash
from werkzeug.utils import secure_filename
from flask_moment import Moment


class FashionStore:

    def __init__(self, db_path='fashion_store.db'):
        self.db_path = db_path
        self.app = Flask(__name__)
        # Trong triển khai thực tế, nên để secret key trong biến môi trường
        self.app.secret_key = os.environ.get('FLASK_SECRET', 'your-secret-key')

        # Tích hợp Flask-Moment để xử lý thời gian trong template
        self.moment = Moment(self.app)

        # Cấu hình thư mục lưu ảnh tải lên
        self.UPLOAD_FOLDER = 'static/uploads'
        self.ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif'}
        self.app.config['UPLOAD_FOLDER'] = self.UPLOAD_FOLDER
        os.makedirs(self.UPLOAD_FOLDER, exist_ok=True)

        # Đăng ký các route (URL) của ứng dụng
        self.register_routes()

    # Các hàm hỗ trợ kết nối & khởi tạo CSDL 
    def get_db_connection(self):
        conn = sqlite3.connect(self.db_path)
        conn.row_factory = sqlite3.Row  # cho phép truy xuất theo tên cột
        return conn

    def init_db(self):
        conn = self.get_db_connection()
        cursor = conn.cursor()

        # Bảng người dùng
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                email TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                full_name TEXT NOT NULL,
                phone TEXT,
                address TEXT,
                role TEXT DEFAULT 'user',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        ''')

        # Bảng danh mục sản phẩm
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS categories (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT UNIQUE NOT NULL,
                description TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        ''')

        # Bảng sản phẩm
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS products (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                description TEXT,
                price REAL NOT NULL,
                category_id INTEGER,
                image_url TEXT,
                stock INTEGER DEFAULT 0,
                size TEXT,
                color TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (category_id) REFERENCES categories (id)
            )
        ''')

        # Bảng giỏ hàng
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS cart (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                product_id INTEGER,
                quantity INTEGER DEFAULT 1,
                size TEXT,
                color TEXT,
                added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users (id),
                FOREIGN KEY (product_id) REFERENCES products (id)
            )
        ''')

        # Bảng đơn hàng
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS orders (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                total_amount REAL NOT NULL,
                status TEXT DEFAULT 'pending',
                customer_name TEXT,
                customer_phone TEXT,
                customer_address TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users (id)
            )
        ''')

        # Bảng chi tiết đơn hàng
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS order_items (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                order_id INTEGER,
                product_id INTEGER,
                quantity INTEGER,
                price REAL,
                size TEXT,
                color TEXT,
                FOREIGN KEY (order_id) REFERENCES orders (id),
                FOREIGN KEY (product_id) REFERENCES products (id)
            )
        ''')

        # Tạo tài khoản admin mặc định nếu chưa có
        cursor.execute("SELECT * FROM users WHERE username='admin'")
        if not cursor.fetchone():
            cursor.execute('''
                INSERT INTO users (username, email, password, full_name, role)
                VALUES (?, ?, ?, ?, ?)
            ''', ('admin', 'admin@example.com', generate_password_hash('admin123'), 'Administrator', 'admin'))

        # Thêm các danh mục mặc định
        default_categories = [
            ('Áo Nam', 'Các loại áo dành cho nam'),
            ('Áo Nữ', 'Các loại áo dành cho nữ'),
            ('Quần Nam', 'Các loại quần dành cho nam'),
            ('Quần Nữ', 'Các loại quần dành cho nữ'),
            ('Giày Dép', 'Giày và dép thời trang'),
            ('Phụ Kiện', 'Phụ kiện thời trang')
        ]
        for name, desc in default_categories:
            cursor.execute('SELECT * FROM categories WHERE name=?', (name,))
            if not cursor.fetchone():
                cursor.execute('INSERT INTO categories (name, description) VALUES (?, ?)', (name, desc))

        # Thêm một số sản phẩm mẫu để hiển thị ban đầu
        sample_products = [
            ('Áo Thun Nam Basic', 'Áo thun nam basic chất cotton thoáng mát', 299000, 1, 'static/images/ao-thun-nam-1.jpg', 50, 'S,M,L,XL', 'Trắng,Đen,Xám'),
            ('Áo Sơ Mi Nam Công Sở', 'Áo sơ mi nam form slim fit', 599000, 1, 'static/images/ao-somi-nam-1.jpg', 30, 'S,M,L,XL,XXL', 'Trắng,Xanh,Hồng'),
            ('Áo Thun Nữ Croptop', 'Áo thun nữ croptop thời trang', 259000, 2, 'static/images/ao-thun-nu-1.jpg', 40, 'S,M,L', 'Hồng,Trắng,Đen'),
            ('Quần Jean Nam Skinny', 'Quần jean nam skinny fit', 799000, 3, 'static/images/quan-jean-nam-1.jpg', 25, '29,30,31,32,33,34', 'Xanh đậm,Xanh nhạt'),
            ('Váy Nữ Vintage', 'Váy nữ vintage xinh xắn', 450000, 4, 'static/images/vay-nu-1.jpg', 20, 'S,M,L', 'Đỏ,Vàng,Xanh'),
            ('Giày Sneaker Unisex', 'Giày sneaker thể thao', 1299000, 5, 'static/images/giay-sneaker-1.jpg', 15, '36,37,38,39,40,41,42,43', 'Trắng,Đen,Xám')
        ]
        for p in sample_products:
            cursor.execute('SELECT * FROM products WHERE name=?', (p[0],))
            if not cursor.fetchone():
                cursor.execute('''
                    INSERT INTO products (name, description, price, category_id, image_url, stock, size, color)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                ''', p)

        conn.commit()
        conn.close()

    # Hàm tiện ích
    def allowed_file(self, filename):
        """Kiểm tra xem file tải lên có đúng định dạng được cho phép không."""
        return '.' in filename and filename.rsplit('.', 1)[1].lower() in self.ALLOWED_EXTENSIONS

    # Đăng ký các route 
    def register_routes(self):
        a = self.app
        a.add_url_rule('/', 'index', self.index)
        a.add_url_rule('/login', 'login', self.login, methods=['GET', 'POST'])
        a.add_url_rule('/register', 'register', self.register, methods=['GET', 'POST'])
        a.add_url_rule('/logout', 'logout', self.logout)
        a.add_url_rule('/products', 'products', self.products)
        a.add_url_rule('/product/<int:product_id>', 'product_detail', self.product_detail)
        a.add_url_rule('/admin', 'admin_dashboard', self.admin_dashboard)

    # Các hàm xử lý giao diện (View) 
    def index(self):
        """Trang chủ: hiển thị danh sách sản phẩm mới và các danh mục."""
        conn = self.get_db_connection()
        products = conn.execute('''
            SELECT p.*, c.name as category_name 
            FROM products p 
            LEFT JOIN categories c ON p.category_id = c.id 
            ORDER BY p.created_at DESC 
            LIMIT 8
        ''').fetchall()
        categories = conn.execute('SELECT * FROM categories').fetchall()
        conn.close()
        return render_template('index.html', products=products, categories=categories)

    def login(self):
        """Đăng nhập người dùng."""
        if request.method == 'POST':
            username = request.form['username']
            password = request.form['password']
            conn = self.get_db_connection()
            user = conn.execute('SELECT * FROM users WHERE username=?', (username,)).fetchone()
            conn.close()
            if user and check_password_hash(user['password'], password):
                session['user_id'] = user['id']
                session['username'] = user['username']
                session['role'] = user['role']
                if user['role'] == 'admin':
                    return redirect(url_for('admin_dashboard'))
                else:
                    return redirect(url_for('index'))
            else:
                flash('Tên đăng nhập hoặc mật khẩu không đúng!')
        return render_template('login.html')

    def register(self):
        """Đăng ký tài khoản người dùng mới."""
        if request.method == 'POST':
            username = request.form['username']
            email = request.form['email']
            password = request.form['password']
            full_name = request.form['full_name']
            phone = request.form.get('phone', '')
            address = request.form.get('address', '')

            conn = self.get_db_connection()
            existing = conn.execute('SELECT * FROM users WHERE username=? OR email=?', (username, email)).fetchone()
            if existing:
                flash('Tên đăng nhập hoặc email đã tồn tại!')
                conn.close()
                return render_template('register.html')

            conn.execute('''
                INSERT INTO users (username, email, password, full_name, phone, address)
                VALUES (?, ?, ?, ?, ?, ?)
            ''', (username, email, generate_password_hash(password), full_name, phone, address))
            conn.commit()
            conn.close()
            flash('Đăng ký thành công!')
            return redirect(url_for('login'))
        return render_template('register.html')

    def logout(self):
        """Đăng xuất tài khoản."""
        session.clear()
        return redirect(url_for('index'))

    def products(self):
        """Trang danh sách sản phẩm, có hỗ trợ tìm kiếm, lọc theo danh mục và giá."""
        conn = self.get_db_connection()
        category_id = request.args.get('category', type=int)
        search = request.args.get('search', '')
        min_price = request.args.get('min_price', type=float)
        max_price = request.args.get('max_price', type=float)

        query = '''
            SELECT p.*, c.name as category_name 
            FROM products p 
            LEFT JOIN categories c ON p.category_id = c.id 
            WHERE 1=1
        '''
        params = []
        if category_id:
            query += ' AND p.category_id=?'
            params.append(category_id)
        if search:
            query += ' AND (p.name LIKE ? OR p.description LIKE ?)'
            params.extend([f'%{search}%', f'%{search}%'])
        if min_price:
            query += ' AND p.price>=?'
            params.append(min_price)
        if max_price:
            query += ' AND p.price<=?'
            params.append(max_price)

        products = conn.execute(query, params).fetchall()
        categories = conn.execute('SELECT * FROM categories').fetchall()
        conn.close()
        return render_template('products.html', products=products, categories=categories)

    def product_detail(self, product_id):
        """Trang chi tiết sản phẩm + gợi ý sản phẩm liên quan."""
        conn = self.get_db_connection()
        product = conn.execute('''
            SELECT p.*, c.name as category_name
            FROM products p
            LEFT JOIN categories c ON p.category_id = c.id
            WHERE p.id=?
        ''', (product_id,)).fetchone()

        if not product:
            flash('Sản phẩm không tồn tại!')
            return redirect(url_for('products'))

        related = conn.execute('''
            SELECT p.*, c.name as category_name
            FROM products p
            LEFT JOIN categories c ON p.category_id = c.id
            WHERE p.category_id=? AND p.id!=?
            LIMIT 4
        ''', (product['category_id'], product_id)).fetchall()

        conn.close()
        return render_template('product_detail.html', product=product, related_products=related)

    def admin_dashboard(self):
        """Trang quản trị hiển thị các thống kê cơ bản."""
        if 'user_id' not in session or session.get('role') != 'admin':
            flash('Bạn không có quyền truy cập!')
            return redirect(url_for('login'))

        conn = self.get_db_connection()
        total_products = conn.execute('SELECT COUNT(*) as c FROM products').fetchone()['c']
        total_users = conn.execute('SELECT COUNT(*) as c FROM users WHERE role="user"').fetchone()['c']
        total_orders = conn.execute('SELECT COUNT(*) as c FROM orders').fetchone()['c']
        total_revenue = conn.execute('SELECT COALESCE(SUM(total_amount),0) as r FROM orders WHERE status="completed"').fetchone()['r']
        recent_orders = conn.execute('''
            SELECT o.*, u.username FROM orders o
            LEFT JOIN users u ON o.user_id=u.id
            ORDER BY o.created_at DESC LIMIT 5
        ''').fetchall()
        conn.close()

        stats = {
            'total_products': total_products,
            'total_users': total_users,
            'total_orders': total_orders,
            'total_revenue': total_revenue
        }
        return render_template('admin/dashboard.html', stats=stats, recent_orders=recent_orders)


# Tạo một instance (đối tượng) của cửa hàng mặc định và liên kết với Flask app
_store = FashionStore()
app = _store.app
init_db = _store.init_db


if __name__ == '__main__':
    # Khi chạy trực tiếp file này, khởi tạo CSDL và chạy server Flask
    init_db()
    app.run(debug=True, host='0.0.0.0', port=5000)
