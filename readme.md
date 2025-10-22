#  Fashion Store - Website Bán Quần Áo

Website bán quần áo hiện đại được xây dựng bằng Flask, SQLite với giao diện đẹp mắt và thân thiện với người dùng.

##  Tính năng chính

###  Dành cho khách hàng (User):
-  Đăng ký/Đăng nhập tài khoản
-  Xem danh sách sản phẩm với bộ lọc tìm kiếm
-  Tìm kiếm sản phẩm theo tên, danh mục, giá
-  Xem chi tiết sản phẩm với hình ảnh, mô tả
-  Thêm sản phẩm vào giỏ hàng
-  Đặt hàng và thanh toán
-  Xem lịch sử đơn hàng
-  Danh sách sản phẩm yêu thích

###  Dành cho quản trị viên (Admin):
-  Dashboard với thống kê tổng quan
-  Quản lý sản phẩm (CRUD)
-  Quản lý danh mục sản phẩm
-  Quản lý đơn hàng và trạng thái
-  Quản lý khách hàng
-  Báo cáo doanh thu và biểu đồ
-  Upload và quản lý hình ảnh

##  Công nghệ sử dụng

- **Backend**: Python Flask
- **Database**: SQLite
- **Frontend**: HTML5, CSS3, JavaScript
- **UI Framework**: Bootstrap 5
- **Icons**: Font Awesome
- **Charts**: Chart.js
- **Authentication**: Werkzeug Security

##  Cấu trúc thư mục

```
deadlineWebBanQuanAo2025_8_13/
├── 📄 app.py                 # File chính Flask application
├── 📄 run.py                 # File chạy server
├── 📄 requirements.txt       # Dependencies Python
├── 📄 README.md             # Hướng dẫn sử dụng
├── 📄 fashion_store.db      # Database SQLite (tự tạo)
├── 📁 templates/            # Các file HTML template
│   ├── 📄 base.html         # Template gốc
│   ├── 📄 index.html        # Trang chủ  
│   ├── 📄 login.html        # Trang đăng nhập
│   ├── 📄 register.html     # Trang đăng ký
│   ├── 📄 products.html     # Danh sách sản phẩm
│   ├── 📄 product_detail.html # Chi tiết sản phẩm
│   └── 📁 admin/            # Templates admin
│       └── 📄 dashboard.html # Dashboard admin
└── 📁 static/               # File tĩnh
    ├── 📁 images/           # Hình ảnh sản phẩm
    │   └── 📄 README.md     # Hướng dẫn ảnh
    └── 📁 uploads/          # Ảnh upload từ admin
```

##  Hướng dẫn cài đặt và chạy

### 1. Cài đặt Python và pip
Đảm bảo bạn đã cài Python 3.7+ và pip

### 2. Cài đặt dependencies
```bash
cd deadlineWebBanQuanAo2025_8_13
pip install -r requirements.txt
```

### 3. Chạy ứng dụng
```bash
python run.py
```

### 4. Truy cập website
Mở trình duyệt và truy cập: `http://localhost:5000`

##  Tài khoản mặc định

### Admin:
- **Username**: `admin`  
- **Password**: `admin123`

### User:
Đăng ký tài khoản mới hoặc sử dụng tài khoản admin để test

##  Giao diện và UX

### Đặc điểm thiết kế:
- **Responsive**: Tương thích mọi thiết bị (desktop, tablet, mobile)
- **Modern**: Giao diện hiện đại với gradient, animations
- **User-friendly**: Dễ sử dụng, trực quan
- **Fast Loading**: Tối ưu hiệu suất tải trang
- **Beautiful**: Màu sắc hài hòa, typography đẹp

### Màu sắc chủ đạo:
- **Primary**: Gradient xanh (#667eea → #764ba2)
- **Success**: Xanh lá (#28a745)
- **Warning**: Vàng cam (#ffc107)  
- **Danger**: Đỏ (#dc3545)
- **Dark**: Xám đen (#2c3e50)

##  Database Schema

### Bảng `users` - Người dùng
```sql
- id (PRIMARY KEY)
- username (UNIQUE)
- email (UNIQUE) 
- password (hashed)
- full_name
- phone
- address
- role (user/admin)
- created_at
```

### Bảng `categories` - Danh mục
```sql
- id (PRIMARY KEY)
- name (UNIQUE)
- description
- created_at
```

### Bảng `products` - Sản phẩm
```sql
- id (PRIMARY KEY)
- name
- description
- price
- category_id (FOREIGN KEY)
- image_url
- stock
- size
- color
- created_at
```

### Bảng `cart` - Giỏ hàng
```sql
- id (PRIMARY KEY)
- user_id (FOREIGN KEY)
- product_id (FOREIGN KEY)
- quantity
- size
- color
- added_at
```

### Bảng `orders` - Đơn hàng
```sql
- id (PRIMARY KEY)
- user_id (FOREIGN KEY)
- total_amount
- status
- customer_name
- customer_phone
- customer_address
- created_at
```

### Bảng `order_items` - Chi tiết đơn hàng
```sql
- id (PRIMARY KEY)
- order_id (FOREIGN KEY)
- product_id (FOREIGN KEY)
- quantity
- price
- size
- color
```

##  Tính năng nâng cao

### Frontend:
-  AJAX cho tương tác không reload trang
-  Animations và transitions mượt mà
-  Live search với debouncing
-  Mobile-first responsive design
-  Image gallery với zoom
-  Real-time cart updates
-  Star rating system
-  Interactive charts (Chart.js)

### Backend:
-  Password hashing với Werkzeug
-  Session management
-  File upload với validation
-  Advanced search và filtering
-  Database relationships
-  Optimized queries
-  Role-based access control

##  Hướng phát triển

### Tính năng có thể mở rộng:
-  Tích hợp payment gateway (Stripe, PayPal, VNPay)
-  Email notifications
-  Live chat support
-  Mobile app (React Native/Flutter)
-  Elasticsearch cho tìm kiếm nâng cao
-  Inventory management
-  Shipping integration
-  Advanced analytics
-  Promotions và discount codes
-  Review và rating system
-  Push notifications
-  Multi-language support

### Technical improvements:
-  Docker containerization
-  Cloud deployment (AWS, Google Cloud)
-  Redis caching
-  API versioning
-  Unit testing
-  API documentation
-  Enhanced security features
-  Performance monitoring

##  Hỗ trợ

Nếu bạn gặp vấn đề hoặc cần hỗ trợ:

1.  Đọc kỹ README này
2.  Kiểm tra console log trong browser
3.  Kiểm tra terminal log khi chạy server
4.  Báo cáo bug với thông tin chi tiết




