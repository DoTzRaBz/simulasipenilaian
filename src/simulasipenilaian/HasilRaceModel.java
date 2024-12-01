package simulasipenilaian;

public class HasilRaceModel {
    private String idHasil;
    private String idRace;
    private String idPembalap;
    private int posisiFinish;
    private int poinDapat;
    private String catatan;

    public String getIdHasil() {
        return idHasil;
    }

    public void setIdHasil(String idHasil) {
        this.idHasil = idHasil;
    }

    public String getIdRace() {
        return idRace;
    }

    public void setIdRace(String idRace) {
        this.idRace = idRace;
    }

    public String getIdPembalap() {
        return idPembalap;
    }

    public void setIdPembalap(String idPembalap) {
        this.idPembalap = idPembalap;
    }

    public int getPosisiFinish() {
        return posisiFinish;
    }

    public void setPosisiFinish(int posisiFinish) {
        this.posisiFinish = posisiFinish;
    }

    public int getPoinDapat() {
        return poinDapat;
    }

    public void setPoinDapat(int poinDapat) {
        this.poinDapat = poinDapat;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}