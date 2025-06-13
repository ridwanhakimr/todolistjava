package academytask;

import java.time.LocalDate;

public class Tugas {
	private int id;
    private String judul;
    private String deskripsi;
    private LocalDate deadline;
    private boolean status; // false = belum selesai, true = selesai

    public Tugas(int id, String judul, String deskripsi, LocalDate deadline) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.deadline = deadline;
        this.status = false;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // Untuk menampilkan status dalam bentuk string di tabel
    public String getStatusString() {
        return status ? "Selesai" : "Belum Selesai";
    }

    // Untuk toggle status antara selesai dan belum selesai
    public void toggleStatus() {
        this.status = !this.status;
    }
}
