package projekakhir;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    // Ganti dengan nama database yang benar
    private static final String URL = "jdbc:mysql://localhost:3306/db_manajemen_keuangan";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Membuat koneksi ke database
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Koneksi ke database berhasil!");
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan koneksi: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        // Test koneksi
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Koneksi ditutup.");
            } catch (SQLException e) {
                System.out.println("Gagal menutup koneksi: " + e.getMessage());
            }
        }
    }  
}
