# Contoh Penggunaan Sistem Bioskop VIP

## Fitur yang Telah Diimplementasikan

### 1. **Konstanta 5 Baris dan 6 Kolom**
```java
private static final int BARIS = 5;
private static final int KOLOM = 6;
```
- Tampilan GUI menampilkan grid 5x6 kursi
- Setiap kursi memiliki label Baris-Kolom (contoh: 1-1, 1-2, ..., 5-6)

### 2. **Nested For Loop untuk Membuat Tombol Kursi**
```java
for (int bar = 0; bar < BARIS; bar++) {
    for (int kol = 0; kol < KOLOM; kol++) {
        tombolKursi[bar][kol] = new JButton("" + (bar + 1) + "-" + (kol + 1));
        // ... setup tombol
    }
}
```
- Membuat 30 tombol kursi (5 baris × 6 kolom)
- Setiap tombol memiliki event listener untuk pemesanan

### 3. **Menu Pilihan (Pesan Kursi dan Keluar)**
- **Tombol "Pesan Kursi"**: Membuka dialog untuk memilih baris dan kolom
- **Tombol "Keluar"**: Menampilkan ringkasan dan keluar dari aplikasi
- **Klik langsung pada kursi**: Alternatif cepat untuk memesan

### 4. **Logika If untuk Harga Kursi**
```java
private int hitungHarga(int baris) {
    if (baris == 1 || baris == 2) {
        return 100000;
    } else if (baris == 3 || baris == 4) {
        return 75000;
    } else {
        return 50000;
    }
}
```
- **Baris 1-2**: Rp 100.000 (VIP Premium)
- **Baris 3-4**: Rp 75.000 (VIP Standard)
- **Baris 5**: Rp 50.000 (VIP Economy)

### 5. **Sistem Pencatatan Data**
- **Array 2D boolean**: Mencatat status kursi (true = terisi, false = kosong)
- **ArrayList<String>**: Menyimpan detail setiap pemesanan
- **Total harga**: Dihitung otomatis berdasarkan pemesanan

### 6. **Validasi Input**
- Validasi nomor baris (1-5) dan kolom (1-6)
- Validasi format angka
- Peringatan jika kursi sudah terisi

## Simulasi Penggunaan

### Scenario 1: Pemesanan Kursi Baris 1
1. Klik tombol "Pesan Kursi"
2. Masukkan Baris: 1, Kolom: 1
3. Konfirmasi pemesanan
4. **Hasil**: 
   - Kursi 1-1 berubah menjadi merah dengan tanda "X"
   - Harga: Rp 100.000
   - Data tercatat: "Kursi: Baris 1, Kolom 1 - Harga: Rp 100000 - Status: Terisi"

### Scenario 2: Pemesanan Kursi Baris 3
1. Klik langsung pada tombol kursi "3-2"
2. Konfirmasi pemesanan
3. **Hasil**:
   - Kursi 3-2 berubah menjadi merah dengan tanda "X"
   - Harga: Rp 75.000
   - Total harga update: Rp 175.000 (jika sebelumnya ada pemesanan)

### Scenario 3: Pemesanan Kursi Baris 5
1. Klik tombol "Pesan Kursi"
2. Masukkan Baris: 5, Kolom: 6
3. Konfirmasi pemesanan
4. **Hasil**:
   - Kursi 5-6 berubah menjadi merah dengan tanda "X"
   - Harga: Rp 50.000

### Scenario 4: Kursi Sudah Terisi
1. Coba pesan kursi yang sudah terisi
2. **Hasil**: Muncul pesan peringatan "Kursi sudah terisi!"

### Scenario 5: Input Tidak Valid
1. Klik "Pesan Kursi"
2. Masukkan Baris: 10, Kolom: 8
3. **Hasil**: Muncul pesan error "Input tidak valid!"

### Scenario 6: Keluar dari Aplikasi
1. Klik tombol "Keluar"
2. **Hasil**: 
   - Muncul ringkasan semua pemesanan
   - Tampilkan total kursi terisi
   - Tampilkan total harga
   - Pesan "Terima kasih telah menggunakan Sistem Bioskop VIP!"

## Output yang Dihasilkan

### Tampilan GUI:
```
BIOSKOP VIP - SISTEM PEMESANAN KURSI

[Grid 5x6 kursi]
[1-1] [1-2] [1-3] [1-4] [1-5] [1-6]
[2-1] [2-2] [2-3] [2-4] [2-5] [2-6]
[3-1] [3-2] [3-3] [3-4] [3-5] [3-6]
[4-1] [4-2] [4-3] [4-4] [4-5] [4-6]
[5-1] [5-2] [5-3] [5-4] [5-5] [5-6]

Total Harga: Rp [jumlah]
```

### Ringkasan Saat Keluar:
```
=== RINGKASAN PEMESANAN ===
Total kursi terisi: 3 dari 30
Total harga: Rp 225000

Detail pemesanan:
- Kursi: Baris 1, Kolom 1 - Harga: Rp 100000 - Status: Terisi
- Kursi: Baris 3, Kolom 2 - Harga: Rp 75000 - Status: Terisi
- Kursi: Baris 5, Kolom 6 - Harga: Rp 50000 - Status: Terisi

Terima kasih telah menggunakan Sistem Bioskop VIP!
```

## Keunggulan Aplikasi
1. **GUI Modern**: Warna menarik, layout terorganisir
2. **User Friendly**: Mudah digunakan dengan dua cara pemesanan
3. **Robust**: Validasi input lengkap, error handling
4. **Fungsional**: Semua requirement terpenuhi
5. **Extendable**: Kode terstruktur untuk pengembangan lebih lanjut