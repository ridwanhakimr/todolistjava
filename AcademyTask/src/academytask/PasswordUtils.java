package academytask;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Formatter;

public class PasswordUtils {

    // Method untuk hash password menggunakan SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Gagal melakukan hashing pada password: " + e.getMessage());
            // Fallback ke plain text jika algoritma tidak ditemukan (seharusnya tidak terjadi)
            return password;
        }
    }

    // Method untuk memverifikasi password
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        String newHash = hashPassword(plainPassword);
        return newHash.equals(hashedPassword);
    }

    // Helper method untuk konversi byte array ke String hex
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        try (Formatter formatter = new Formatter(sb)) {
            for (byte b : bytes) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        }
    }
}