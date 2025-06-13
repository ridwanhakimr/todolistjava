package academytask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // URL, USER, dan PASSWORD untuk mengakses database
    private static final String URL = "jdbc:mysql://localhost:3306/academy_task_db";  // ganti dengan nama database kamu
    private static final String USER = "root";  // default user MySQL
    private static final String PASS = "";  // default password kosong

    // Method untuk menghubungkan ke database
    public static Connection connect() {
        try {
            // Menggunakan DriverManager untuk mendapatkan koneksi
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            // Menangani error koneksi
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}
