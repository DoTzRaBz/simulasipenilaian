package simulasipenilaian;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBRace {

    private RaceModel dt = new RaceModel();

    public RaceModel getRaceModel() {
        return dt;
    }

    public void setRaceModel(RaceModel s) {
        dt = s;
    }

    public ObservableList<RaceModel> Load() {
        ObservableList<RaceModel> tableData = FXCollections.observableArrayList();
        Koneksi con = new Koneksi();

        try {
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();

            // Debug: Cetak query
            System.out.println("Executing query: SELECT id_race, nama_race, tanggal, id_sirkuit, kategori, status, "
                    + "COALESCE(musim, YEAR(CURRENT_DATE)) AS musim, "
                    + "COALESCE(round_ke, 1) AS round_ke "
                    + "FROM race_event");

            ResultSet rs = con.statement.executeQuery(
                    "SELECT id_race, nama_race, tanggal, id_sirkuit, kategori, status, "
                    + "COALESCE(musim, YEAR(CURRENT_DATE)) AS musim, "
                    + "COALESCE(round_ke, 1) AS round_ke "
                    + "FROM race_event"
            );

            // Debug: Cek apakah ResultSet kosong
            if (!rs.isBeforeFirst()) {
                System.out.println("Tidak ada data race ditemukan.");
            }

            while (rs.next()) {
                RaceModel d = new RaceModel();
                d.setIdRace(rs.getString("id_race"));
                d.setNamaRace(rs.getString("nama_race"));
                d.setTanggal(rs.getDate("tanggal"));
                d.setIdSirkuit(rs.getString("id_sirkuit"));
                d.setKategori(rs.getString("kategori"));
                d.setStatus(rs.getString("status"));

                // Debug: Cetak setiap data yang diload
                System.out.println("Loading Race: " + d.getIdRace() + " - " + d.getNamaRace());

                // Gunakan COALESCE untuk menangani null
                d.setMusim(rs.getInt("musim"));
                d.setRoundKe(rs.getInt("round_ke"));

                tableData.add(d);
            }

            System.out.println("Data race berhasil dimuat: " + tableData.size() + " record(s)");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gagal memuat data race: " + e.getMessage());
        } finally {
            con.tutupKoneksi();
        }
        return tableData;
    }

    public int validasi(String nomor) {
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT COUNT(*) AS jml FROM race_event WHERE id_race = '" + nomor + "'");
            if (rs.next()) {
                return rs.getInt("jml");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            con.tutupKoneksi();
        }
    }

    public boolean insert() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(
                    "INSERT INTO race_event (id_race, nama_race, tanggal, id_sirkuit, kategori, status, musim, round_ke) VALUES (?,?,?,?,?,?,?,?)");

            con.preparedStatement.setString(1, getRaceModel().getIdRace());
            con.preparedStatement.setString(2, getRaceModel().getNamaRace());
            con.preparedStatement.setDate(3, new java.sql.Date(getRaceModel().getTanggal().getTime()));
            con.preparedStatement.setString(4, getRaceModel().getIdSirkuit());
            con.preparedStatement.setString(5, getRaceModel().getKategori());

            // Potong status jika terlalu panjang
            String status = getRaceModel().getStatus();
            if (status != null && status.length() > 20) { // sesuaikan dengan panjang kolom
                status = status.substring(0, 20);
            }
            con.preparedStatement.setString(6, status);

            con.preparedStatement.setInt(7, getRaceModel().getMusim());
            con.preparedStatement.setInt(8, getRaceModel().getRoundKe());

            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (SQLException e) {
            // Tambahkan logging detail error
            System.err.println("Error inserting race: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            berhasil = false;
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean update() {
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(
                    "UPDATE race_event SET "
                    + "nama_race = ?, id_sirkuit = ?, kategori = ?, "
                    + "tanggal = ?, musim = ?, round_ke = ?, status = ? "
                    + "WHERE id_race = ?"
            );
            con.preparedStatement.setString(1, dt.getNamaRace());
            con.preparedStatement.setString(2, dt.getIdSirkuit());
            con.preparedStatement.setString(3, dt.getKategori());
            con.preparedStatement.setDate(4, dt.getTanggal());

            // Tentukan musim
            int musim = dt.getMusim();
            if (musim == 0 && dt.getTanggal() != null) {
                musim = dt.getTanggal().toLocalDate().getYear();
            } else if (musim == 0) {
                musim = Year.now().getValue();
            }
            con.preparedStatement.setInt(5, musim);

            // Tentukan round_ke
            int roundKe = dt.getRoundKe();
            if (roundKe == 0) {
                roundKe = 1;
            }
            con.preparedStatement.setInt(6, roundKe);

            con.preparedStatement.setString(7, dt.getStatus());
            con.preparedStatement.setString(8, dt.getIdRace());

            con.preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal update race: " + e.getMessage());
            return false;
        } finally {
            con.tutupKoneksi();
        }
    }

    public boolean delete(String nomor) {
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(
                    "DELETE FROM race_event WHERE id_race = ?"
            );
            con.preparedStatement.setString(1, nomor);
            con.preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal menghapus race: " + e.getMessage());
            return false;
        } finally {
            con.tutupKoneksi();
        }
    }
}
