# Boilerplate JavaFX Dashboard

Berikut adalah langkah-langkah untuk menggunakan Boilerplate JavaFX Dashboard.

---

## 1. Install Laragon/XAMPP/Lainnya dan Tambahkan Library JDBC

## Langkah 1: Unduh dan Install Laragon atau XAMPP
- **Laragon**: Unduh dari [Laragon Official Website](https://laragon.org/download/).  
- **XAMPP**: Unduh dari [XAMPP Official Website](https://www.apachefriends.org/index.html).

Keduanya menyediakan lingkungan pengembangan yang lengkap dengan server web dan database. Pilih salah satu sesuai kebutuhan Anda.

---

## Langkah 2: Konfigurasi Database dengan phpMyAdmin
- **phpMyAdmin**: Gunakan [phpMyAdmin](https://www.phpmyadmin.net/) untuk mempermudah pengelolaan database MySQL/MariaDB.  
- Pastikan Anda dapat mengakses phpMyAdmin melalui Laragon atau XAMPP setelah instalasi. Biasanya diakses melalui `http://localhost/phpmyadmin`.

---

## Langkah 3: Tambahkan Library JDBC untuk MySQL
Pastikan Anda memiliki driver JDBC untuk database yang Anda gunakan. Untuk MySQL, Anda bisa mengunduh driver dari [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) atau menggunakan Maven dengan menambahkan dependensi berikut di `pom.xml`:

```xml
<dependencies>
    <dependency>
      <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.32</version>
    </dependency>
</dependencies>
```

---