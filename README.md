# Sistem Bioskop VIP - UTS Pemrograman Berbasis Objek

Proyek ini dibuat untuk memenuhi tugas **Ujian Tengah Semester (UTS)**.

---

## Identitas Mahasiswa

*   **Nama:** Supardi Akhiyat
*   **NIM:** 230101010026

---

## Prasyarat Sistem (Prerequisites)
Sebelum menjalankan program ini, pastikan sistem Anda telah memiliki:
1. **Java Development Kit (JDK)** versi 8 atau yang lebih baru terinstal dan dikonfigurasi di dalam PATH sistem.
2. **Visual Studio Code** (opsional) dengan ekstensi **Extension Pack for Java** untuk kemudahan kompilasi dan eksekusi.

---

## Struktur File Proyek
* `BioskopVIP.java`
* `BioskopLogic.java`
* `flatlaf-3.7.1.jar`
* `logo.png`
* `.vscode/settings.json`

---

## Panduan Instalasi dan Menjalankan Aplikasi

### Metode 1: Menggunakan Terminal / Command Prompt (Manual)

1. **Buka Terminal / Command Prompt** dan masuk ke direktori proyek ini:
   ```bash
   cd "d:\Kuliah\semester 6\Pemrograman Berbasis Object\UTS\soal 2"
   ```

2. **Kompilasi Program:**
   Gunakan perintah kompilasi `javac` dengan menyertakan classpath library `flatlaf-3.7.1.jar`.
   * **Windows (Command Prompt / PowerShell):**
     ```cmd
     javac -cp ".;flatlaf-3.7.1.jar" BioskopLogic.java BioskopVIP.java
     ```
   * **macOS / Linux (Terminal):**
     ```bash
     javac -cp ".:flatlaf-3.7.1.jar" BioskopLogic.java BioskopVIP.java
     ```

3. **Jalankan Aplikasi:**
   Gunakan perintah `java` dengan classpath yang sama untuk mengeksekusi kelas utama `BioskopVIP`.
   * **Windows (Command Prompt / PowerShell):**
     ```cmd
     java -cp ".;flatlaf-3.7.1.jar" BioskopVIP
     ```
   * **macOS / Linux (Terminal):**
     ```bash
     java -cp ".:flatlaf-3.7.1.jar" BioskopVIP
     ```

