package simulasipenilaian;

public class PembalapModel {

    private String idPembalap;
    private String namaPembalap;
    private String tim;
    private int poin;
    private int posisi;

    // Getter
    public String getIdPembalap() {
        return idPembalap;
    }

    public String getNamaPembalap() {
        return namaPembalap;
    }

    public String getTim() {
        return tim;
    }

    public int getPoin() {
        return poin;
    }

    public int getPosisi() {
        return posisi;
    }

    // Setter
    public void setIdPembalap(String idPembalap) {
        this.idPembalap = idPembalap;
    }

    public void setNamaPembalap(String namaPembalap) {
        this.namaPembalap = namaPembalap;
    }

    public void setTim(String tim) {
        this.tim = tim;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public void setPosisi(int posisi) {
        this.posisi = posisi;
    }
}
