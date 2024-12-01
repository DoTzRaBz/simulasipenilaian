package simulasipenilaian;

public class RaceModel {

    private String idRace;
    private String namaRace;
    private java.sql.Date tanggal;
    private String idSirkuit;
    private String kategori;
    private String status;
    private int musim;
    private int roundKe;

    // Getter
    public String getIdRace() {
        return idRace;
    }

    public String getNamaRace() {
        return namaRace;
    }

    public java.sql.Date getTanggal() {
        return tanggal;
    }

    public String getIdSirkuit() {
        return idSirkuit;
    }

    public String getKategori() {
        return kategori;
    }

    public String getStatus() {
        return status;
    }

    public int getMusim() {
        return musim;
    }

    public int getRoundKe() {
        return roundKe;
    }

    // Setter
    public void setIdRace(String idRace) {
        this.idRace = idRace;
    }

    public void setNamaRace(String namaRace) {
        this.namaRace = namaRace;
    }

    public void setTanggal(java.sql.Date tanggal) {
        this.tanggal = tanggal;
    }

    public void setIdSirkuit(String idSirkuit) {
        this.idSirkuit = idSirkuit;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMusim(int musim) {
        this.musim = musim;
    }

    public void setRoundKe(int roundKe) {
        this.roundKe = roundKe;
    }
}
