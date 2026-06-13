# Sistem Bioskop VIP - Aplikasi Java GUI Modern

## Deskripsi
Aplikasi Java GUI modern untuk sistem pemesanan kursi bioskop VIP dengan fitur-fitur lengkap sesuai permintaan.

## Fitur
1. **Tampilan GUI Modern** dengan warna yang menarik dan user-friendly
2. **5 Baris dan 6 Kolom** kursi (sesuai konstanta)
3. **Menu Pilihan**:
   - Pesan Kursi
   - Keluar
4. **Sistem Pemesanan**:
   - Pilih baris dan kolom
   - Validasi input
   - Tanda (X) untuk kursi terisi
5. **Logika Harga**:
   - Baris 1-2: Rp 100.000
   - Baris 3-4: Rp 75.000
   - Baris 5: Rp 50.000
6. **Sistem Pencatatan Data**:
   - Mencatat semua pemesanan
   - Menghitung total harga
   - Menampilkan ringkasan saat keluar

## Cara Menjalankan
1. Pastikan Java Development Kit (JDK) terinstal
2. Compile file Java:
   ```
   javac BioskopVIP.java
   ```
3. Jalankan aplikasi:
   ```
   java BioskopVIP
   ```

## Struktur Kode
- **BioskopVIP.java**: File utama aplikasi
- **Konstanta**: BARIS = 5, KOLOM = 6
- **Array 2D**: Untuk status kursi (true/false)
- **Nested For Loop**: Untuk membuat dan mengelola tombol kursi
- **Logika If**: Untuk menentukan harga berdasarkan baris

## Screenshot Aplikasi
Aplikasi memiliki tampilan yang terdiri dari:
1. Header dengan judul "BIOSKOP VIP - SISTEM PEMESANAN KURSI"
2. Grid kursi 5x6 dengan tombol berwarna
3. Panel informasi di sebelah kanan
4. Tombol menu "Pesan Kursi" dan "Keluar"
5. Display total harga

## Contoh Penggunaan
1. Klik tombol "Pesan Kursi" atau klik langsung pada kursi yang diinginkan
2. Masukkan nomor baris (1-5) dan kolom (1-6)
3. Konfirmasi pemesanan
4. Kursi akan ditandai dengan (X) dan berubah warna menjadi merah
5. Data pemesanan akan tercatat di panel informasi
6. Total harga akan terupdate otomatis
7. Pilih "Keluar" untuk menutup aplikasi dan melihat ringkasan

## Teknologi
- Java Swing untuk GUI
- Event-driven programming
- Object-oriented design
- Exception handling untuk validasi input