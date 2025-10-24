# 🛍️ Fashion Store - E-commerce Application

> Ứng dụng web bán hàng thời trang được xây dựng bằng **Java Spring Boot**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## 📋 Mục Lục

- [Giới Thiệu](#-giới-thiệu)
- [Tính Năng](#-tính-năng)
- [Công Nghệ Sử Dụng](#-công-nghệ-sử-dụng)
- [Yêu Cầu Hệ Thống](#-yêu-cầu-hệ-thống)
- [Cài Đặt & Chạy](#-cài-đặt--chạy)
- [Cấu Trúc Dự Án](#-cấu-trúc-dự-án)
- [Cấu Hình Database](#-cấu-hình-database)
- [Tài Khoản Mặc Định](#-tài-khoản-mặc-định)
- [API Endpoints](#-api-endpoints)
- [Tài Liệu Tham Khảo](#-tài-liệu-tham-khảo)
- [Contributors](#-contributors)
- [License](#-license)

---

## 🎯 Giới Thiệu

**Fashion Store** là một ứng dụng thương mại điện tử (E-commerce) hoàn chỉnh chuyên về thời trang, được phát triển với **Java Spring Boot Framework**. Dự án cung cấp đầy đủ các chức năng từ quản lý sản phẩm, giỏ hàng, đặt hàng cho khách hàng đến quản lý đơn hàng cho admin.

### ✨ Điểm Nổi Bật

- 🏗️ **Kiến trúc MVC** chuẩn với Spring Boot
- 🔐 **Bảo mật** với Spring Security
- 💾 **ORM** với Hibernate & JPA
- 🎨 **Giao diện** responsive với Bootstrap 5
- 📦 **Quản lý state** với Spring Session
- 🖼️ **Upload ảnh** cho sản phẩm
- 📱 **Mobile-friendly** design

---

## 🚀 Tính Năng

### 👥 Dành Cho Khách Hàng

#### 🔐 Xác Thực & Tài Khoản
- ✅ Đăng ký tài khoản mới
- ✅ Đăng nhập/Đăng xuất
- ✅ Quản lý thông tin cá nhân
- ✅ Bảo mật mật khẩu với BCrypt

#### 🛒 Mua Sắm
- ✅ Xem danh sách sản phẩm
- ✅ Tìm kiếm & lọc sản phẩm theo danh mục
- ✅ Xem chi tiết sản phẩm (ảnh, mô tả, giá, size, màu)
- ✅ Thêm sản phẩm vào giỏ hàng
- ✅ Cập nhật số lượng trong giỏ
- ✅ Xóa sản phẩm khỏi giỏ
- ✅ Thanh toán nhanh ngay tại giỏ hàng
- ✅ Đặt hàng với thông tin giao hàng

#### 📦 Quản Lý Đơn Hàng
- ✅ Xem lịch sử đơn hàng
- ✅ Theo dõi trạng thái đơn hàng
- ✅ Xem chi tiết từng đơn hàng

### 👨‍💼 Dành Cho Admin

#### 📊 Dashboard
- ✅ Thống kê tổng quan (doanh thu, đơn hàng, sản phẩm)
- ✅ Biểu đồ và báo cáo

#### 📦 Quản Lý Đơn Hàng
- ✅ Xem tất cả đơn hàng
- ✅ Lọc đơn hàng theo trạng thái
- ✅ **Xác nhận đơn hàng** (pending → processing)
- ✅ **Cập nhật trạng thái giao hàng** (processing → shipping)
- ✅ **Hoàn thành đơn hàng** (shipping → completed)
- ✅ **Hủy đơn hàng** (any → cancelled)
- ✅ Xem chi tiết đơn hàng
- ✅ Timeline trạng thái đơn hàng

#### 🏷️ Quản Lý Sản Phẩm
- ✅ Thêm/Sửa/Xóa sản phẩm
- ✅ Upload ảnh sản phẩm
- ✅ Quản lý danh mục
- ✅ Quản lý tồn kho

---

## 💻 Công Nghệ Sử Dụng

### Backend Framework
- **Java 17** - Programming Language
- **Spring Boot 3.2.0** - Application Framework
- **Spring MVC** - Web Framework
- **Spring Data JPA** - Data Access Layer
- **Spring Security** - Authentication & Authorization
- **Hibernate** - ORM Framework
- **Maven** - Build Tool & Dependency Management

### Frontend
- **Thymeleaf** - Server-side Template Engine
- **Bootstrap 5.3.0** - CSS Framework
- **Font Awesome 6.4.0** - Icons
- **JavaScript** - Client-side Scripting
- **HTML5 & CSS3** - Markup & Styling

### Database
- **H2 Database** - Development (In-memory)
- **MySQL** - Production (Optional)
- **PostgreSQL** - Production (Optional)

### Libraries & Tools
- **Lombok** - Reduce Boilerplate Code
- **Jakarta Validation** - Input Validation
- **Spring Boot DevTools** - Hot Reload
- **HikariCP** - Connection Pooling

---

## 📋 Yêu Cầu Hệ Thống

- **Java Development Kit (JDK)** 17 hoặc cao hơn
- **Apache Maven** 3.6+ 
- **Git** (để clone repository)
- **(Optional)** MySQL 8.0+ hoặc PostgreSQL 13+ cho production

### Kiểm Tra Phiên Bản

```bash
# Kiểm tra Java version
java -version

# Kiểm tra Maven version
mvn -version

# Kiểm tra Git version
git --version
```

---

## 🔧 Cài Đặt & Chạy

### 1️⃣ Clone Repository

```bash
git clone https://github.com/phmphongg/CSE703029-N01-Nhom5.git
cd CSE703029-N01-Nhom5
```

### 2️⃣ Build Project

```bash
# Clean & build project
mvn clean install

# Hoặc skip tests để build nhanh hơn
mvn clean install -DskipTests
```

### 3️⃣ Chạy Application

**Cách 1: Sử dụng Maven**
```bash
mvn spring-boot:run
```

**Cách 2: Chạy JAR file**
```bash
java -jar target/fashion-store-1.0.0.jar
```

**Cách 3: Sử dụng script (Windows)**
```powershell
.\run.ps1
```

### 4️⃣ Truy Cập Ứng Dụng

- **Trang chủ:** http://localhost:8080
- **Trang sản phẩm:** http://localhost:8080/products
- **Đăng nhập:** http://localhost:8080/login
- **Admin Dashboard:** http://localhost:8080/admin
- **H2 Console (Dev):** http://localhost:8080/h2-console

### 5️⃣ Dừng Ứng Dụng

```bash
# Nhấn Ctrl + C trong terminal
# Hoặc kill process theo port
```

**Windows PowerShell:**
```powershell
Get-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess | Stop-Process
```

---

## 📁 Cấu Trúc Dự Án

```
CSE703029-N01-nhom5/
├── src/
│   ├── main/
│   │   ├── java/com/fashionstore/
│   │   │   ├── FashionStoreApplication.java      # Main Application Class
│   │   │   │
│   │   │   ├── model/                             # Entity Classes (JPA)
│   │   │   │   ├── User.java                     # User entity
│   │   │   │   ├── Product.java                  # Product entity
│   │   │   │   ├── Category.java                 # Category entity
│   │   │   │   ├── Cart.java                     # Cart entity
│   │   │   │   ├── Order.java                    # Order entity
│   │   │   │   └── OrderItem.java                # OrderItem entity
│   │   │   │
│   │   │   ├── repository/                        # Data Access Layer (Spring Data JPA)
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   ├── CategoryRepository.java
│   │   │   │   ├── CartRepository.java
│   │   │   │   ├── OrderRepository.java
│   │   │   │   └── OrderItemRepository.java
│   │   │   │
│   │   │   ├── service/                           # Business Logic Layer
│   │   │   │   ├── UserService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   ├── CategoryService.java
│   │   │   │   ├── CartService.java
│   │   │   │   └── OrderService.java
│   │   │   │
│   │   │   ├── controller/                        # Web Layer (MVC Controllers)
│   │   │   │   ├── HomeController.java           # Homepage
│   │   │   │   ├── AuthController.java           # Login/Register
│   │   │   │   ├── ProductController.java        # Product pages
│   │   │   │   ├── CartController.java           # Shopping cart
│   │   │   │   ├── OrderController.java          # Order management
│   │   │   │   ├── AdminController.java          # Admin panel
│   │   │   │   └── FileUploadController.java     # File upload
│   │   │   │
│   │   │   └── config/                            # Configuration Classes
│   │   │       ├── SecurityConfig.java           # Spring Security config
│   │   │       ├── CustomUserDetailsService.java # User authentication
│   │   │       ├── WebConfig.java                # MVC & static resources
│   │   │       └── DataInitializer.java          # Sample data initialization
│   │   │
│   │   └── resources/
│   │       ├── application.properties             # Application configuration
│   │       │
│   │       ├── templates/                         # Thymeleaf Templates
│   │       │   ├── index.html                    # Homepage
│   │       │   ├── login.html                    # Login page
│   │       │   ├── register.html                 # Register page
│   │       │   ├── products.html                 # Product list
│   │       │   ├── product_detail.html           # Product detail
│   │       │   ├── cart.html                     # Shopping cart
│   │       │   ├── checkout.html                 # Checkout page
│   │       │   ├── orders.html                   # Order history
│   │       │   ├── order_confirmation.html       # Order confirmation
│   │       │   │
│   │       │   ├── admin/                        # Admin templates
│   │       │   │   ├── dashboard.html            # Admin dashboard
│   │       │   │   ├── orders.html               # Order management
│   │       │   │   └── order_detail.html         # Order detail
│   │       │   │
│   │       │   └── fragments/                    # Reusable fragments
│   │       │       └── navbar.html               # Navigation bar
│   │       │
│   │       └── static/                            # Static Resources
│   │           └── images/                        # Product images
│   │               ├── ao-thun-nam-1.jpg
│   │               ├── ao-somi-nam-1.jpg
│   │               ├── ao-thun-nu-1.jpg
│   │               ├── quan-jean-nam-1.png
│   │               ├── vay-nu-1.jpg
│   │               └── giay-sneaker-1.jpg
│   │
│   └── test/                                      # Test Classes
│
├── data/                                          # H2 Database Files
│   └── fashionstore.mv.db
│
├── static/uploads/                                # Uploaded Files (Runtime)
│
├── target/                                        # Build Output
│   └── fashion-store-1.0.0.jar
│
├── pom.xml                                        # Maven Dependencies
├── run.ps1                                        # Run script (Windows)
└── README.md                                      # This file
```

---

## 🗄️ Cấu Hình Database

### H2 Database (Development - Mặc Định)

H2 là database in-memory được sử dụng cho development.

**File cấu hình:** `src/main/resources/application.properties`

```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:file:./data/fashionstore
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

**Truy cập H2 Console:**
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/fashionstore`
- Username: `sa`
- Password: (để trống)

### MySQL (Production)

**Bước 1:** Uncomment MySQL dependency trong `pom.xml`

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

**Bước 2:** Tạo database

```sql
CREATE DATABASE fashion_store 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

**Bước 3:** Cập nhật `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fashion_store?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

### PostgreSQL (Production)

**Bước 1:** Uncomment PostgreSQL dependency trong `pom.xml`

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

**Bước 2:** Tạo database

```sql
CREATE DATABASE fashion_store
WITH ENCODING 'UTF8'
LC_COLLATE='vi_VN.UTF-8'
LC_CTYPE='vi_VN.UTF-8';
```

**Bước 3:** Cập nhật `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fashion_store
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

---

## 🔑 Tài Khoản Mặc Định

Khi khởi động lần đầu tiên, hệ thống tự động tạo:

### Admin Account
- **Username:** `admin`
- **Password:** `admin123`
- **Role:** `ADMIN`
- **Quyền:** Quản lý toàn bộ hệ thống

### Sample Categories
- Áo Nam
- Áo Nữ
- Quần Nam
- Quần Nữ
- Giày Dép
- Phụ Kiện

### Sample Products
- Áo Thun Nam Basic - 299,000₫
- Áo Sơ Mi Nam Công Sở - 599,000₫
- Áo Thun Nữ Croptop - 259,000₫
- Quần Jean Nam Skinny - 799,000₫
- Váy Nữ Vintage - 450,000₫
- Giày Sneaker Unisex - 1,299,000₫

---

## 🌐 API Endpoints

### 🔐 Authentication

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/login` | Trang đăng nhập | No |
| POST | `/login` | Xử lý đăng nhập | No |
| GET | `/register` | Trang đăng ký | No |
| POST | `/register` | Xử lý đăng ký | No |
| GET | `/logout` | Đăng xuất | Yes |

### 🏠 Public Pages

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/` | Trang chủ | No |
| GET | `/products` | Danh sách sản phẩm | No |
| GET | `/products/{id}` | Chi tiết sản phẩm | No |

### 🛒 Shopping Cart

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/cart` | Xem giỏ hàng | Yes (USER) |
| POST | `/cart/add` | Thêm vào giỏ | Yes (USER) |
| POST | `/cart/update/{id}` | Cập nhật số lượng | Yes (USER) |
| POST | `/cart/remove/{id}` | Xóa sản phẩm | Yes (USER) |
| POST | `/cart/clear` | Xóa tất cả | Yes (USER) |

### 📦 Orders

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/orders` | Lịch sử đơn hàng | Yes (USER) |
| GET | `/orders/{id}` | Chi tiết đơn hàng | Yes (USER) |
| POST | `/checkout` | Đặt hàng | Yes (USER) |
| GET | `/order-confirmation` | Xác nhận đơn hàng | Yes (USER) |

### 👨‍💼 Admin

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/admin` | Admin dashboard | Yes (ADMIN) |
| GET | `/admin/orders` | Quản lý đơn hàng | Yes (ADMIN) |
| GET | `/admin/orders/{id}` | Chi tiết đơn hàng | Yes (ADMIN) |
| POST | `/admin/orders/{id}/confirm` | Xác nhận đơn | Yes (ADMIN) |
| POST | `/admin/orders/{id}/ship` | Giao hàng | Yes (ADMIN) |
| POST | `/admin/orders/{id}/complete` | Hoàn thành | Yes (ADMIN) |
| POST | `/admin/orders/{id}/cancel` | Hủy đơn | Yes (ADMIN) |
| POST | `/admin/orders/{id}/update-status` | Cập nhật trạng thái | Yes (ADMIN) |

### 📤 File Upload

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/upload/product-image` | Upload ảnh sản phẩm | Yes (ADMIN) |

---

## 🔄 Order Status Workflow

```
📋 PENDING (Chờ xác nhận)
    ↓ [Admin: Xác nhận đơn]
⚙️ PROCESSING (Đang xử lý)
    ↓ [Admin: Giao hàng]
🚚 SHIPPING (Đang giao)
    ↓ [Admin: Hoàn thành]
✅ COMPLETED (Hoàn thành)

❌ CANCELLED (Đã hủy) ← [Admin/User: Hủy đơn từ bất kỳ trạng thái nào]
```

---

## 🎨 Screenshots

### Trang Chủ
![Homepage](docs/screenshots/homepage.png)

### Danh Sách Sản Phẩm
![Products](docs/screenshots/products.png)

### Giỏ Hàng
![Cart](docs/screenshots/cart.png)

### Admin Dashboard
![Admin](docs/screenshots/admin-dashboard.png)

### Quản Lý Đơn Hàng
![Orders](docs/screenshots/admin-orders.png)

---

## 📚 Tài Liệu Tham Khảo

### 📖 Official Documentation

#### Spring Framework
- **Spring Boot** - https://spring.io/projects/spring-boot
- **Spring Data JPA** - https://spring.io/projects/spring-data-jpa
- **Spring Security** - https://spring.io/projects/spring-security
- **Spring MVC** - https://docs.spring.io/spring-framework/reference/web/webmvc.html

#### Template Engine
- **Thymeleaf** - https://www.thymeleaf.org/
- **Thymeleaf + Spring Security** - https://www.thymeleaf.org/doc/articles/springsecurity.html

#### Database
- **H2 Database** - https://www.h2database.com/
- **Hibernate ORM** - https://hibernate.org/orm/documentation/

### 🎨 Frontend Libraries
- **Bootstrap 5** - https://getbootstrap.com/ (v5.3.0)
- **Font Awesome** - https://fontawesome.com/ (v6.4.0)
- **Google Fonts** - https://fonts.google.com/

### 🛠️ Build Tools & Libraries
- **Apache Maven** - https://maven.apache.org/
- **Lombok** - https://projectlombok.org/

### 📸 Image Resources
- **Unsplash** - https://unsplash.com/ (Free stock photos)
- **Pexels** - https://www.pexels.com/ (Free stock photos)
- **Pixabay** - https://pixabay.com/ (Free stock photos)

### 📝 Learning Resources
- **Baeldung** - https://www.baeldung.com/ (Spring Boot tutorials)
- **Spring Guides** - https://spring.io/guides (Official guides)
- **Viblo** - https://viblo.asia/ (Vietnamese tech blog)

### 🔧 Development Tools
- **IntelliJ IDEA** - https://www.jetbrains.com/idea/
- **Visual Studio Code** - https://code.visualstudio.com/
- **Postman** - https://www.postman.com/
- **DBeaver** - https://dbeaver.io/

### 📊 Stack Overflow Tags
- `[spring-boot]` - https://stackoverflow.com/questions/tagged/spring-boot
- `[spring-data-jpa]` - https://stackoverflow.com/questions/tagged/spring-data-jpa
- `[spring-security]` - https://stackoverflow.com/questions/tagged/spring-security
- `[thymeleaf]` - https://stackoverflow.com/questions/tagged/thymeleaf

---

## 🛠️ Troubleshooting

### Port đã được sử dụng

**Lỗi:** `Web server failed to start. Port 8080 was already in use.`

**Giải pháp 1:** Thay đổi port trong `application.properties`
```properties
server.port=8081
```

**Giải pháp 2:** Kill process đang dùng port 8080
```powershell
# Windows PowerShell
Get-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess | Stop-Process -Force
```

### Database Connection Errors

**Kiểm tra:**
- Database đã được tạo chưa
- Username/password có đúng không
- Database service có đang chạy không
- Connection string có chính xác không

### Build Errors

```bash
# Clean & rebuild
mvn clean install -U

# Xóa cache Maven
mvn dependency:purge-local-repository
```

### Lombok Not Working

**IntelliJ IDEA:**
1. Install Lombok plugin
2. Enable annotation processing:
   - Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - Check "Enable annotation processing"

**VS Code:**
1. Install "Java Extension Pack"
2. Install "Lombok Annotations Support"

---

## 📝 Development Notes

### Code Style
- Follow Java naming conventions
- Use Lombok to reduce boilerplate
- Write meaningful variable/method names
- Add comments for complex logic

### Git Workflow
```bash
# Tạo branch mới
git checkout -b feature/your-feature-name

# Commit changes
git add .
git commit -m "feat: add your feature description"

# Push to remote
git push origin feature/your-feature-name

# Create Pull Request on GitHub
```

### Testing
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=YourTestClass

# Skip tests during build
mvn clean install -DskipTests
```

---

## 🤝 Contributors

- **Team CSE703029-N01-Nhom5**
  - Repository: https://github.com/phmphongg/CSE703029-N01-Nhom5

---

## 📄 License

This project is licensed under the MIT License.

```
MIT License

Copyright (c) 2025 CSE703029-N01-Nhom5

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 📞 Support & Contact

Nếu có thắc mắc hoặc cần hỗ trợ, vui lòng:
- 🐛 Tạo issue trên [GitHub Issues](https://github.com/phmphongg/CSE703029-N01-Nhom5/issues)
- 📧 Email: [your-email@example.com]
- 📖 Tham khảo [Documentation](https://github.com/phmphongg/CSE703029-N01-Nhom5/wiki)

---

<div align="center">

**⭐ Star this repo if you find it helpful! ⭐**

Made with ❤️ by Team CSE703029-N01-Nhom5

</div>
