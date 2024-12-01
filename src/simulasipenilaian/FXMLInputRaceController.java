package simulasipenilaian;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class FXMLInputRaceController {
    @FXML private TextField txtIdRace;
    @FXML private TextField txtNamaRace;
    @FXML private DatePicker dpTanggal;
    @FXML private TextField txtIdSirkuit;
    @FXML private ComboBox<String> cbKategori;
    @FXML private ComboBox<String> cbStatus;
    @FXML private TextField txtMusim;
    @FXML private TextField txtRoundKe;

    private DBRace dbRace = new DBRace();

    @FXML
    private void initialize() {
        // Tambahkan listener untuk membatasi panjang input
        addTextLimiter(txtIdRace, 10);
        addTextLimiter(txtNamaRace, 50);
        addTextLimiter(txtIdSirkuit, 10);
        addTextLimiter(txtMusim, 4);
        addTextLimiter(txtRoundKe, 2);

        // Atur pilihan ComboBox
        cbKategori.getItems().addAll("Formula 1", "Formula 2", "MotoGP", "Moto2", "Moto3");
        cbStatus.getItems().addAll("Dijadwalkan", "Berlangsung", "Selesai", "Ditunda", "Dibatalkan");
        
        // Set default status
        cbStatus.setValue("Dijadwalkan");
    }

    // Method untuk membatasi panjang input
    private void addTextLimiter(TextField textField, int maxLength) {
        textField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (textField.getText().length() > maxLength) {
                String s = textField.getText().substring(0, maxLength);
                textField.setText(s);
            }
        });
    }

    @FXML
private void handleSave() {
    try {
        // Validasi input
        if (!validateInput()) {
            return;
        }

        RaceModel rm = new RaceModel();
        rm.setIdRace(txtIdRace.getText());
        rm.setNamaRace(txtNamaRace.getText());
        
        // Konversi LocalDate ke java.sql.Date
        java.sql.Date sqlDate = java.sql.Date.valueOf(dpTanggal.getValue());
        rm.setTanggal(sqlDate);
        
        rm.setIdSirkuit(txtIdSirkuit.getText());
        rm.setKategori(cbKategori.getValue());
        rm.setStatus(cbStatus.getValue());
        rm.setMusim(Integer.parseInt(txtMusim.getText()));
        rm.setRoundKe(Integer.parseInt(txtRoundKe.getText()));

        DBRace dbRace = new DBRace();
        dbRace.setRaceModel(rm);

        if (dbRace.insert()) {
            showSuccessAlert("Data Race berhasil disimpan");
            ((Stage)txtIdRace.getScene().getWindow()).close();
        } else {
            showAlert("Gagal menyimpan data Race");
        }
    } catch (NumberFormatException e) {
        showAlert("Musim dan Round harus berupa angka!");
    } catch (Exception e) {
        showAlert("Error: " + e.getMessage());
        e.printStackTrace();
    }
}
    @FXML
    private void handleBatal() {
        ((Stage) txtIdRace.getScene().getWindow()).close();
    }

    private boolean validateInput() {
        if (txtIdRace.getText().isEmpty()) {
            showAlert("ID Race harus diisi");
            return false;
        }
        if (txtNamaRace.getText().isEmpty()) {
            showAlert("Nama Race harus diisi");
            return false;
        }
        if (dpTanggal.getValue() == null) {
            showAlert("Tanggal harus dipilih");
            return false;
        }
        if (txtIdSirkuit.getText().isEmpty()) {
            showAlert("ID Sirkuit harus diisi");
            return false;
        }
        if (cbKategori.getValue() == null) {
            showAlert("Kategori harus dipilih");
            return false;
        }
        if (cbStatus.getValue() == null) {
            showAlert("Status harus dipilih");
            return false;
        }
        if (txtMusim.getText().isEmpty()) {
            showAlert("Musim harus diisi");
            return false;
        }
        if (txtRoundKe.getText().isEmpty()) {
            showAlert("Round Ke harus diisi");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Kesalahan Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtIdRace.clear();
        txtNamaRace.clear();
        dpTanggal.setValue(null);
        txtIdSirkuit.clear();
        cbKategori.setValue(null);
        cbStatus.setValue("Dijadwalkan");
        txtMusim.clear();
        txtRoundKe.clear();
    }
}