# Petunjuk Menggunakan FlatLaf di Aplikasi BioskopVIP

Untuk menjalankan aplikasi dengan tampilan FlatLaf yang modern, ikuti langkah-langkah berikut:

## Langkah 1: Download FlatLaf JAR

1. Buka halaman rilis FlatLaf di GitHub:
   https://github.com/JFormDesigner/FlatLaf/releases

2. Download file `flatlaf-<version>.jar` (contoh: `flatlaf-3.4.1.jar`)

3. Tempatkan file JAR tersebut di folder yang sama dengan `BioskopVIP.java`

## Langkah 2: Compile Aplikasi

Buka terminal/command prompt dan jalankan:

```powershell
cd "d:\Kuliah\semester 6\Pemrograman Berbasis Object\UTS\soal 2"
javac -cp ".;flatlaf-3.7.1.jar" BioskopVIP.java
```

Ganti `<version>` dengan versi yang Anda download (contoh: `flatlaf-3.4.1.jar`).

## Langkah 3: Jalankan Aplikasi

Jalankan aplikasi dengan:

```powershell
java -cp ".;flatlaf-3.7.1.jar" BioskopVIP
```

## Pilih Tema

Anda bisa mengganti tema di file `BioskopVIP.java`, method `main`:

- **Tema Terang (Default)**: `FlatLightLaf`
- **Tema Gelap**: Ganti dengan `FlatDarkLaf`

Contoh untuk tema gelap:
```java
UIManager.setLookAndFeel(new FlatDarkLaf());
```

Setelah mengganti, compile dan jalankan kembali aplikasi!
