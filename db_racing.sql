-- Buat database
CREATE DATABASE db_racing;

-- Gunakan database
USE db_racing;

-- Tabel Pembalap
CREATE TABLE pembalap (
    id_pembalap VARCHAR(10) PRIMARY KEY,
    nama_pembalap VARCHAR(100) NOT NULL,
    tim VARCHAR(100) NOT NULL,
    total_poin INT DEFAULT 0,
    posisi_klasemen INT DEFAULT 0
);

-- Tabel Race Event
CREATE TABLE race_event (
    id_race VARCHAR(10) PRIMARY KEY,
    nama_race VARCHAR(100) NOT NULL,
    tanggal DATE NOT NULL,
    id_sirkuit VARCHAR(10) NOT NULL,
    kategori ENUM('F1', 'MotoGP') NOT NULL,
    status ENUM('Selesai', 'Akan Datang', 'Berlangsung') DEFAULT 'Akan Datang',
    musim INT,
    round_ke INT DEFAULT 1
);

-- Contoh data Pembalap
INSERT INTO pembalap (id_pembalap, nama_pembalap, tim, total_poin, posisi_klasemen) VALUES
('P001', 'Max Verstappen', 'Red Bull Racing', 125, 1),
('P002', 'Lewis Hamilton', 'Mercedes', 110, 2),
('P003', 'Charles Leclerc', 'Ferrari', 95, 3);

-- Contoh data Race Event
INSERT INTO race_event (id_race, nama_race, tanggal, id_sirkuit, kategori, status, musim, round_ke) VALUES
('R001', 'Bahrain Grand Prix', '2023-03-05', 'S001', 'F1', 'Selesai', 2023, 1),
('R002', 'Saudi Arabian Grand Prix', '2023-03-19', 'S002', 'F1', 'Selesai', 2023, 2),
('R003', 'Australian Grand Prix', '2023-04-02', 'S003', 'F1', 'Selesai', 2023, 3);