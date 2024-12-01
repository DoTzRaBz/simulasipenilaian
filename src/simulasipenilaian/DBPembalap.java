package simulasipenilaian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBPembalap {
    private PembalapModel dt = new PembalapModel();

    public PembalapModel getPembalapModel() {
        return dt;
    }

    public void setPembalapModel(PembalapModel s) {
        dt = s;
    }

    public ObservableList<PembalapModel> Load() {
    ObservableList<PembalapModel> tableData = FXCollections.observableArrayList();
    Koneksi con = new Koneksi();
    try {
        // Debug: Pastikan koneksi terbuka
        Connection connection = con.bukaKoneksi();
        if (connection == null) {
            System.out.println("Koneksi database gagal!");
            return tableData;
        }

        con.statement = connection.createStatement();
        
        // Debug: Cetak query yang dijalankan
        System.out.println("Executing query: SELECT id_pembalap, nama_pembalap, tim, total_poin, posisi_klasemen FROM pembalap");
        
        ResultSet rs = con.statement.executeQuery("SELECT id_pembalap, nama_pembalap, tim, total_poin, posisi_klasemen FROM pembalap");
        
        // Debug: Cek apakah ResultSet kosong
        if (!rs.isBeforeFirst()) {
            System.out.println("Tidak ada data pembalap ditemukan.");
        }
        
        while (rs.next()) {
            PembalapModel d = new PembalapModel();
            
            // Debug: Cetak setiap data yang diload
            System.out.println("Loading Pembalap: " + 
                rs.getString("id_pembalap") + " - " + 
                rs.getString("nama_pembalap")
            );
            
            d.setIdPembalap(rs.getString("id_pembalap"));
            d.setNamaPembalap(rs.getString("nama_pembalap"));
            d.setTim(rs.getString("tim"));
            d.setPoin(rs.getInt("total_poin"));
            d.setPosisi(rs.getInt("posisi_klasemen"));
            tableData.add(d);
        }
        
        System.out.println("Data pembalap berhasil dimuat: " + tableData.size() + " record(s)");
    } catch (SQLException e) {
        // Tangani exception dengan lebih detail
        System.err.println("Database error occurred:");
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error Code: " + e.getErrorCode());
        System.err.println("Message: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        // Tangani exception umum
        System.err.println("Unexpected error occurred:");
        e.printStackTrace();
    } finally {
        con.tutupKoneksi();
    }
    return tableData;
}    
    public int validasi(String nomor) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from pembalap where id_pembalap = '" + nomor + "'");
            while (rs.next()) {
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public boolean insert() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("insert into pembalap (id_pembalap, nama_pembalap, tim, total_poin, posisi_klasemen) values (?,?,?,?,?)");
            con.preparedStatement.setString(1, getPembalapModel().getIdPembalap());
            con.preparedStatement.setString(2, getPembalapModel().getNamaPembalap());
            con.preparedStatement.setString(3, getPembalapModel().getTim());
            con.preparedStatement.setInt(4, getPembalapModel().getPoin());
            con.preparedStatement.setInt(5, getPembalapModel().getPosisi());
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            berhasil = false;
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean delete(String nomor) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from pembalap where id_pembalap = ? ");
            con.preparedStatement.setString(1, nomor);
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }

    public boolean update() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(
                    "update pembalap set nama_pembalap = ?, tim = ?, total_poin = ?, posisi_klasemen = ? where id_pembalap = ? ");
            con.preparedStatement.setString(1, getPembalapModel().getNamaPembalap());
            con.preparedStatement.setString(2, getPembalapModel().getTim());
            con.preparedStatement.setInt(3, getPembalapModel().getPoin());
            con.preparedStatement.setInt(4, getPembalapModel().getPosisi());
            con.preparedStatement.setString(5, getPembalapModel().getIdPembalap());
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {
            e.printStackTrace();
            berhasil = false;
        } finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }
}