package academytask;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TugasDAO {

    // Menyimpan data tugas ke dalam database DENGAN user_id
    public static void addTugas(Tugas tugas) {
        String query = "INSERT INTO tugas (judul, deskripsi, deadline, user_id) VALUES (?, ?, ?, ?)"; // <-- Tambahkan user_id
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, tugas.getJudul());
            stmt.setString(2, tugas.getDeskripsi());
            stmt.setDate(3, Date.valueOf(tugas.getDeadline()));
            stmt.setString(4, tugas.getUserId()); // <-- Set user_id

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menyimpan tugas: " + e.getMessage());
        }
    }

    // Mengambil semua tugas dari database BERDASARKAN userId
    public static List<Tugas> getTugasByUser(String userId) {
        List<Tugas> tugasList = new ArrayList<>();
        String query = "SELECT * FROM tugas WHERE user_id = ?"; // <-- Filter berdasarkan user_id
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            
        	while (rs.next()) {
        	    int id = rs.getInt("id");
        	    String judul = rs.getString("judul");
        	    String deskripsi = rs.getString("deskripsi");
        	    LocalDate deadline = rs.getDate("deadline").toLocalDate();
        	    boolean status = rs.getBoolean("status");
        	    
                Tugas tugas = new Tugas(id, judul, deskripsi, deadline);
        	    tugas.setStatus(status);
        	    tugas.setUserId(rs.getString("user_id")); // Set userId pada objek tugas
        	    
                tugasList.add(tugas);
        	}
        } catch (SQLException e) {
            System.out.println("Gagal mengambil tugas: " + e.getMessage());
        }
        return tugasList;
    }

    // Menghapus tugas dari database (Tidak perlu diubah)
    public static void deleteTugas(Tugas tugas) {
        String query = "DELETE FROM tugas WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, tugas.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menghapus tugas: " + e.getMessage());
        }
    }

    
    // Memperbarui tugas (Tidak perlu diubah, karena kita update berdasarkan ID tugas yang unik)
    public static void updateTugas(Tugas tugas) {
        String query = "UPDATE tugas SET judul = ?, deskripsi = ?, deadline = ?, status = ? WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tugas.getJudul());
            stmt.setString(2, tugas.getDeskripsi());
            stmt.setDate(3, Date.valueOf(tugas.getDeadline()));
            stmt.setBoolean(4, tugas.isStatus());
            stmt.setInt(5, tugas.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal memperbarui tugas: " + e.getMessage());
        }
    }
}