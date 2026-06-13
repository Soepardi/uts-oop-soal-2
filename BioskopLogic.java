import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BioskopLogic {
    public static final int BARIS = 5;
    public static final int KOLOM = 6;

    private boolean[][] kursi;
    private boolean[][] kursiDipilih;
    private List<String> dataPemesanan;
    private int totalHarga;

    public BioskopLogic() {
        kursi = new boolean[BARIS][KOLOM];
        kursiDipilih = new boolean[BARIS][KOLOM];
        dataPemesanan = new ArrayList<>();
        totalHarga = 0;
    }

    public int getHarga(int baris) {
        if (baris == 1 || baris == 2) {
            return 100000;
        } else if (baris == 3 || baris == 4) {
            return 75000;
        } else {
            return 50000;
        }
    }

    public boolean isKursiTerisi(int baris, int kolom) {
        return kursi[baris][kolom];
    }

    public boolean isKursiDipilih(int baris, int kolom) {
        return kursiDipilih[baris][kolom];
    }

    public void setKursiDipilih(int baris, int kolom, boolean status) {
        kursiDipilih[baris][kolom] = status;
    }

    public void togglePilihKursi(int baris, int kolom) {
        if (!kursi[baris][kolom]) {
            kursiDipilih[baris][kolom] = !kursiDipilih[baris][kolom];
        }
    }

    public void resetPilihanKursi() {
        for (int bar = 0; bar < BARIS; bar++) {
            for (int kol = 0; kol < KOLOM; kol++) {
                kursiDipilih[bar][kol] = false;
            }
        }
    }

    public int getTotalHargaSementara() {
        int totalSementara = 0;
        for (int bar = 0; bar < BARIS; bar++) {
            for (int kol = 0; kol < KOLOM; kol++) {
                if (kursiDipilih[bar][kol]) {
                    totalSementara += getHarga(bar + 1);
                }
            }
        }
        return totalSementara;
    }

    public int getJumlahDipilih() {
        int jumlahDipilih = 0;
        for (int bar = 0; bar < BARIS; bar++) {
            for (int kol = 0; kol < KOLOM; kol++) {
                if (kursiDipilih[bar][kol]) {
                    jumlahDipilih++;
                }
            }
        }
        return jumlahDipilih;
    }

    public String getNamaKursi(int baris, int kolom) {
        char barisChar = (char) ('A' + baris);
        return String.valueOf(barisChar) + (kolom + 1);
    }

    // Mengembalikan array {waktu, namaKursi, hargaStr} untuk ditambahkan ke tabel UI
    public String[] pesanKursi(int baris, int kolom) {
        if (kursi[baris][kolom]) {
            return null; // Sudah terisi
        }

        int harga = getHarga(baris + 1);
        kursi[baris][kolom] = true;
        kursiDipilih[baris][kolom] = false;
        totalHarga += harga;

        String waktu = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String namaKursi = getNamaKursi(baris, kolom);
        String hargaStr = "Rp " + String.format("%,d", harga);

        String data = "[" + waktu + "] Kursi " + namaKursi + " | " + hargaStr;
        dataPemesanan.add(data);

        return new String[]{waktu, namaKursi, hargaStr};
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public List<String> getDataPemesanan() {
        return dataPemesanan;
    }

    public int hitungKursiTerisi() {
        int count = 0;
        for (int bar = 0; bar < BARIS; bar++) {
            for (int kol = 0; kol < KOLOM; kol++) {
                if (kursi[bar][kol]) {
                    count++;
                }
            }
        }
        return count;
    }
}
