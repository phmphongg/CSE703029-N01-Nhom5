#!/usr/bin/env python3
"""
Fashion Store - Website bán quần áo
Chạy file này để khởi động server

"""

from app import app, init_db
import os

if __name__ == '__main__':
    # Khởi tạo database
    init_db()
    
    # Thiết lập môi trường
    port = int(os.environ.get('PORT', 5000))
    debug = os.environ.get('DEBUG', 'True').lower() == 'true'
    host = os.environ.get('HOST', '0.0.0.0')
    
    print("=" * 50)
    print("  FASHION STORE - WEBSITE BÁN QUẦN ÁO")
    print("=" * 50)
    print(f" Server đang chạy tại: http://localhost:{port}")
    print(f" Debug mode: {debug}")
    print(" Tài khoản admin mặc định:")
    print("    Username: admin")
    print("    Password: admin123")
    print("=" * 50)
    print(" Cấu trúc thư mục:")
    print("    templates/ - Các file HTML")
    print("    static/ - CSS, JS, Images")
    print("    static/uploads/ - Ảnh upload")
    print("    fashion_store.db - Database SQLite")
    print("=" * 50)
    print(" Khởi động server...")
    
    try:
        app.run(
            host=host,
            port=port, 
            debug=debug,
            threaded=True
        )
    except KeyboardInterrupt:
        print("\n⏹  Server đã dừng.")
    except Exception as e:
        print(f" Lỗi khởi động server: {e}")
