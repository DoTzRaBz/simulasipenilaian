package simulasipenilaian;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class FXMLController {
    @FXML private Button btnAddPembalap;
    @FXML private Button btnAddRace;
@FXML private TableView<PembalapModel> tablePembalap;
@FXML private TableColumn<PembalapModel, String> colIdPembalap;
@FXML private TableColumn<PembalapModel, String> colNamaPembalap;
@FXML private TableColumn<PembalapModel, String> colTim;
@FXML private TableColumn<PembalapModel, Integer> colPoin;
@FXML private TableColumn<PembalapModel, Integer> colPosisi;
@FXML private Button btnHapusPembalap;  
    
    private DBPembalap dbPembalap = new DBPembalap();
    private DBRace dbRace = new DBRace();

  @FXML private TableView<RaceModel> tableRace;
@FXML private TableColumn<RaceModel, String> colIdRace;
@FXML private TableColumn<RaceModel, String> colNamaRace;
@FXML private TableColumn<RaceModel, Date> colTanggal;
@FXML private TableColumn<RaceModel, String> colIdSirkuit;
@FXML private TableColumn<RaceModel, String> colKategori;
@FXML private TableColumn<RaceModel, String> colStatus;
@FXML private TableColumn<RaceModel, Integer> colMusim;
@FXML private TableColumn<RaceModel, Integer> colRoundKe;
@FXML private Button btnHapusRace; 
@FXML public void initialize() {
    try {
        // Inisialisasi kolom untuk Tabel Pembalap
        colIdPembalap.setCellValueFactory(new PropertyValueFactory<>("idPembalap"));
        colNamaPembalap.setCellValueFactory(new PropertyValueFactory<>("namaPembalap"));
        colTim.setCellValueFactory(new PropertyValueFactory<>("tim"));
        colPoin.setCellValueFactory(new PropertyValueFactory<>("poin"));
        colPosisi.setCellValueFactory(new PropertyValueFactory<>("posisi"));

        // Inisialisasi kolom untuk Tabel Race
        colIdRace.setCellValueFactory(new PropertyValueFactory<>("idRace"));
        colNamaRace.setCellValueFactory(new PropertyValueFactory<>("namaRace"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colIdSirkuit.setCellValueFactory(new PropertyValueFactory<>("idSirkuit"));
        colKategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colMusim.setCellValueFactory(new PropertyValueFactory<>("musim"));
        colRoundKe.setCellValueFactory(new PropertyValueFactory<>("roundKe"));

        // Debug
        System.out.println("Initializing controller, loading data...");

        // Load data Pembalap
        if (tablePembalap != null) {
            ObservableList<PembalapModel> pembalapData = dbPembalap.Load();
            System.out.println("Loaded pembalap data: " + pembalapData.size() + " records");
            tablePembalap.setItems(pembalapData);
        } else {
            System.out.println("tablePembalap is null!");
        }

        // Load data Race
        if (tableRace != null) {
            ObservableList<RaceModel> raceData = dbRace.Load();
            System.out.println("Loaded race data: " + raceData.size() + " records");
            tableRace.setItems(raceData);
        } else {
            System.out.println("tableRace is null!");
        }

    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Initialization Error", "Failed to load data: " + e.getMessage());
    }
}

// Metode showAlert untuk menampilkan pesan error
private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}
private void loadPembalapData() {
    try {
        // Reload data pembalap ke tabel
        ObservableList<PembalapModel> pembalapData = dbPembalap.Load();
        tablePembalap.setItems(pembalapData);
        
        // Optional: Refresh tabel
        tablePembalap.refresh();
        
        System.out.println("Data Pembalap dimuat ulang: " + pembalapData.size() + " record");
    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Kesalahan", "Gagal memuat data pembalap: " + e.getMessage());
    }
}

private void loadRaceData() {
    try {
        // Reload data race ke tabel
        ObservableList<RaceModel> raceData = dbRace.Load();
        tableRace.setItems(raceData);
        
        // Optional: Refresh tabel
        tableRace.refresh();
        
        System.out.println("Data Race dimuat ulang: " + raceData.size() + " record");
    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Kesalahan", "Gagal memuat data race: " + e.getMessage());
    }
}
    @FXML private void handleAddPembalap() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputPembalap.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Tambah Pembalap Baru");
            stage.setScene(new Scene(root));

            stage.setOnCloseRequest(event -> loadPembalapData());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Kesalahan", "Gagal Membuka Form Input Pembalap: " + e.getMessage());
        }
    }

    @FXML private void handleAddRace() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputRace.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage ();
            stage.setTitle("Tambah Race Baru");
            stage.setScene(new Scene(root));

            stage.setOnCloseRequest(event -> loadRaceData());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Kesalahan", "Gagal Membuka Form Input Race: " + e.getMessage());
        }
    }
    @FXML
private void handleHapusPembalap() {
    PembalapModel selectedPembalap = tablePembalap.getSelectionModel().getSelectedItem();
    if (selectedPembalap != null) {
        try {
            // Konfirmasi penghapusan
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Konfirmasi Hapus");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Apakah Anda yakin ingin menghapus pembalap: " + 
                selectedPembalap.getNamaPembalap() + "?");
            
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Panggil method delete dari DBPembalap
                boolean deleted = dbPembalap.delete(selectedPembalap.getIdPembalap());                
                if (deleted) {
                    // Reload data setelah berhasil dihapus
                    loadPembalapData();
                    
                    // Tampilkan pesan sukses
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Hapus Pembalap");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Pembalap berhasil dihapus.");
                    successAlert.showAndWait();
                } else {
                    // Tampilkan pesan gagal
                    showAlert("Kesalahan", "Gagal menghapus pembalap.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Kesalahan", "Terjadi kesalahan saat menghapus pembalap: " + e.getMessage());
        }
    } else {
        // Tampilkan pesan jika tidak ada pembalap yang dipilih
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Hapus Pembalap");
        alert.setHeaderText(null);
        alert.setContentText("Pilih pembalap yang ingin dihapus.");
        alert.showAndWait();
    }
}

@FXML
private void handleHapusRace() {
    RaceModel selectedRace = tableRace.getSelectionModel().getSelectedItem();
    if (selectedRace != null) {
        try {
            // Konfirmasi penghapusan
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Konfirmasi Hapus");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Apakah Anda yakin ingin menghapus race: " + 
                selectedRace.getNamaRace() + "?");
            
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Panggil method delete dari DBRace
                boolean deleted = dbRace.delete(selectedRace.getIdRace());
                
                if (deleted) {
                    // Reload data setelah berhasil dihapus
                    loadRaceData();
                    
                    // Tampilkan pesan sukses
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Hapus Race");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Race berhasil dihapus.");
                    successAlert.showAndWait();
                } else {
                    // Tampilkan pesan gagal
                    showAlert("Kesalahan", "Gagal menghapus race.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Kesalahan", "Terjadi kesalahan saat menghapus race: " + e.getMessage());
        }
    } else {
        // Tampilkan pesan jika tidak ada race yang dipilih
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Hapus Race");
        alert.setHeaderText(null);
        alert.setContentText("Pilih race yang ingin dihapus.");
        alert.showAndWait();
    }
}

}