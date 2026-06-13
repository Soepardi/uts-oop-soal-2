# Analisis Kode Program - Sistem Bioskop VIP

Sistem Bioskop VIP ini dibangun menggunakan bahasa pemrograman Java dengan pustaka antarmuka grafis (GUI) standar **Java Swing** serta dukungan pihak ketiga **FlatDarkLaf** untuk tampilan modern bertema gelap (*Dark Mode*). 

Berikut adalah analisis komponen, struktur, dan fungsionalitas dari kode sumber `BioskopVIP.java`.

## 1. Struktur Data dan Manajemen *State*
Program merepresentasikan tata letak bioskop sebagai matriks dengan ukuran konstan 5 Baris dan 6 Kolom. Manajemen status (apakah kursi kosong, dipilih, atau sudah dipesan) menggunakan Array dua dimensi bertipe data boolean.

```java
private static final int BARIS = 5;
private static final int KOLOM = 6;

// Array untuk melacak status pemesanan final
private boolean[][] kursi = new boolean[BARIS][KOLOM];
// Array untuk melacak status pilihan sementara dari pengguna
private boolean[][] kursiDipilih = new boolean[BARIS][KOLOM];

// List untuk melacak jejak riwayat pemesanan string
private List<String> dataPemesanan = new ArrayList<>();
```

## 2. Inisialisasi Tema Modern (FlatDarkLaf)
Bagian utama (*entry point*) program berada pada metode `main`. Disini terjadi inisiasi *Look and Feel* kustom dengan `FlatDarkLaf` (library UI eksternal) serta properti kustom (seperti radius *border*) agar UI terlihat modern dan mulus (*rounded*).

```java
public static void main(String[] args) {
    try {
        FlatDarkLaf.setup(); // Memuat tema UI
        
        // Membulatkan tepi komponen (Rounded corners)
        UIManager.put("Button.arc", 15);
        UIManager.put("Component.arc", 15);
        UIManager.put("TextComponent.arc", 12);
        
        // Mengubah warna panel default menjadi dark zinc
        UIManager.put("Panel.background", new Color(24, 24, 27));
    } catch (Exception e) {
        // Fallback jika library gagal dimuat
    }
    SwingUtilities.invokeLater(BioskopVIP::new);
}
```

## 3. Palet Warna (*Custom Theming*)
Pengembang secara rinci mendeklarasikan konstanta `Color` (kode RGB hex-based zinc dan aksen warna) yang memberikan indikasi visual kepada pengguna tentang status UI.
```java
private final Color COLOR_SEAT_AVAILABLE = new Color(34, 197, 94); // Hijau (Tersedia)
private final Color COLOR_SEAT_SELECTED = new Color(59, 130, 246);  // Biru (Dipilih Sementara)
private final Color COLOR_SEAT_OCCUPIED = new Color(63, 63, 70);     // Abu-abu Gelap (Terisi)
```

## 4. Pembuatan Tombol Kursi Secara Dinamis (Grid)
Alih-alih membuat variabel satu persatu untuk setiap tombol, program merendernya secara matematis lewat perulangan (*Nested Loop*). Kursi disusun di dalam kontainer yang memiliki *LayoutManager* `GridLayout`.
```java
panelKursi = new JPanel(new GridLayout(BARIS + 1, KOLOM + 1, 15, 15));
tombolKursi = new JButton[BARIS][KOLOM];

for (int bar = 0; bar < BARIS; bar++) {
    // Label karakter A, B, C... untuk baris
    // ...
    for (int kol = 0; kol < KOLOM; kol++) {
        // Membuat tombol kursi dan menyimpannya di Array 2D
        tombolKursi[bar][kol] = createSeatButton(getNamaKursi(bar, kol), bar, kol);

        // Menambahkan Action Listener agar tombol merespon saat diklik
        final int barisFinal = bar;
        final int kolomFinal = kol;
        tombolKursi[bar][kol].addActionListener(e -> togglePilihKursi(barisFinal, kolomFinal));

        panelKursi.add(tombolKursi[bar][kol]);
    }
}
```

## 5. Logika Algoritma Penentuan Harga Tiket
Harga tiket menggunakan aturan berdasarkan kedekatan kursi dengan layar (baris). Baris depan adalah premium (Rp 100.000) dan baris belakang lebih terjangkau.
```java
private int hitungHarga(int baris) {
    if (baris == 1 || baris == 2) {
        return 100000; // VIP Premium (Baris A, B)
    } else if (baris == 3 || baris == 4) {
        return 75000;  // VIP Standard (Baris C, D)
    } else {
        return 50000;  // VIP Economy (Baris E)
    }
}
```

## 6. Proses Konfirmasi & Log Riwayat Pemesanan (`JTable`)
Ketika pengguna menekan tombol "PESAN KURSI", fungsi akan mengecek koleksi array `kursiDipilih`. Program akan menampilkan struk tagihan ke pengguna dalam `JOptionPane`. Saat dikonfirmasi, baris baru ditambahkan ke tabel log riwayat secara aktual (dengan format *waktu* hh:mm:ss).
```java
if (konfirmasi == JOptionPane.YES_OPTION) {
    // 1. Pembaruan State dan Antarmuka Tombol
    kursi[bar][kol] = true;         // Tetapkan status menjadi terisi
    kursiDipilih[bar][kol] = false; // Hapus dari cache sementara
    
    tombolKursi[bar][kol].setText("✗");
    tombolKursi[bar][kol].setBackground(COLOR_SEAT_OCCUPIED);
    tombolKursi[bar][kol].setEnabled(false); // Disable tombol
    
    // 2. Logging tabel riwayat pemesanan
    String waktu = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
    modelTabel.addRow(new Object[]{waktu, namaKursi, hargaStr});
}
```

## 7. Kalkulasi Ringkasan saat Aplikasi Ditutup (Checkout)
Pemberhentian program secara anggun (*graceful shutdown*) terjadi saat pengguna mengklik tombol **KELUAR**. Sebuah dialog akan muncul meringkas penjualan keseluruhan; termasuk sisa kursi kosong dan rekap harga keseluruhan (*Grand Total*).
```java
private void keluarAplikasi() {
    int kursiTerisi = hitungKursiTerisi();
    int totalKursi = BARIS * KOLOM;

    StringBuilder ringkasan = new StringBuilder();
    ringkasan.append("Total Kursi  : " + totalKursi + "\n");
    ringkasan.append("Terisi       : " + kursiTerisi + "\n");
    ringkasan.append("Kosong       : " + (totalKursi - kursiTerisi) + "\n");
    ringkasan.append("Total Harga  : Rp " + String.format("%,d", totalHarga) + "\n");

    // Menampilkan pesan JOptionPane lalu System.exit
    JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Ringkasan", JOptionPane.INFORMATION_MESSAGE);
    System.exit(0);
}
```

## Kesimpulan
`BioskopVIP.java` merupakan contoh implementasi sistem GUI berbasis Event-Driven yang canggih dengan menerapkan pengelolaan Status Koleksi (array 2D), pemisahan antara Model UI dan Logic Data, UI Kustom menggunakan render FlatLaf, serta penanganan aksi klik (ActionListeners & MouseAdapters).