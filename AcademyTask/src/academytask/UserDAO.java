package academytask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    // Method untuk menghasilkan ID pengguna berikutnya (misal: UID0001)
    private static String generateNextUserId() {
        String lastId = null;
        String query = "SELECT id FROM users ORDER BY id DESC LIMIT 1";
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                lastId = rs.getString("id");
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil ID terakhir: " + e.getMessage());
            return "UID0001"; // Fallback jika terjadi error
        }

        if (lastId == null) {
            return "UID0001"; // Jika tabel masih kosong
        }

        // Ambil bagian angka dari ID, increment, lalu format kembali
        int num = Integer.parseInt(lastId.substring(3));
        return String.format("UID%04d", num + 1);
    }

    // Method untuk registrasi pengguna baru
    public static boolean registerUser(User user) {
        String checkUserQuery = "SELECT * FROM users WHERE username = ? OR email = ?";
        String insertQuery = "INSERT INTO users (id, name, username, password, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect()) {
            // Cek apakah username atau email sudah ada
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery)) {
                checkStmt.setString(1, user.getUsername());
                checkStmt.setString(2, user.getEmail());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Username atau email sudah terdaftar.");
                    return false; // Registrasi gagal
                }
            }

            // Jika belum ada, lanjutkan registrasi
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, generateNextUserId());
                insertStmt.setString(2, user.getName());
                insertStmt.setString(3, user.getUsername());
                insertStmt.setString(4, PasswordUtils.hashPassword(user.getPassword())); // Hash password
                insertStmt.setString(5, user.getEmail());
                insertStmt.executeUpdate();
                return true; // Registrasi berhasil
            }
        } catch (SQLException e) {
            System.out.println("Gagal mendaftarkan pengguna: " + e.getMessage());
            return false;
        }
    }

    // Method untuk memeriksa login
    public static User checkLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                // Verifikasi password yang diinput dengan yang ada di DB
                if (PasswordUtils.verifyPassword(password, storedHashedPassword)) {
                    return new User(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        storedHashedPassword,
                        rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal melakukan login: " + e.getMessage());
        }
        return null; // Login gagal
    }
}