# TP6DPBO2425C

Program ini merupakan implementasi permainan **Flappy Bird** sederhana menggunakan **Java Swing** dengan konsep **Object-Oriented Programming (OOP)**.  
Game memiliki menu, gameplay utama, sistem skor, restart, dan sistem kembali ke menu.

---

## 🤝🏻 Janji

Saya Nadhif Arva Anargya dengan NIM 2404336 mengerjakan Tugas Praktikum 6 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek. Untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

---

## 🎨 Desain Program

Program ini dirancang menggunakan pendekatan **OOP** dan struktur modular untuk memisahkan antara logika permainan dan tampilan grafis.

### 📂 Struktur Kelas

| Kelas | Deskripsi |
|------|-----------|
`App.java` | Entry point aplikasi, menginisialisasi JFrame dan menampilkan menu |
`Menu.java` | Tampilan menu awal (Start & Exit) |
`Logic.java` | Logika permainan (gravitasi, gerakan player, pipa, skor, game over) |
`View.java` | Menggambar player, pipa, background, UI teks & skor |
`Player.java` | Representasi burung, menyimpan posisi, ukuran, kecepatan, dan gambar |
`Pipe.java` | Representasi pipa, menyimpan posisi, ukuran, gambar, status pipa |

### 🧠 Konsep OOP

| Konsep | Implementasi dalam Program |
|--------|---------------------------|
Encapsulation | Variabel `private` dalam `Player` dan `Pipe` dengan getter/setter |
Abstraction | Logika permainan dipisahkan dari tampilan (`Logic` vs `View`) |
Event Driven | Input keyboard memakai `KeyListener`, game memakai `Timer` |
Modularitas | Setiap kelas punya tugas spesifik (sesuai Single Responsibility) |

### 🧩 Fitur Utama

- Menu awal (Start & Exit)
- Player terbang dengan tombol **SPACE**
- Pipa muncul otomatis dan bergerak dari kanan
- Sistem skor & deteksi melewati pipa
- Game Over state
- **Restart** dengan tombol `R`
- **Kembali ke Menu** dengan tombol `M`

---

## 🔄 Penjelasan Alur Program

1. **Program dimulai melalui `App.java`**
   - JFrame dibuat
   - Menu utama tampil (`Menu.java`)

2. **User memilih Start**
   - Sistem membuat objek `Logic` & `View`
   - Game dimulai

3. **Gameplay**
   - Player muncul di tengah layar
   - Tekan **SPACE** untuk mulai dan terbang
   - Timer game (`Timer`) bekerja setiap frame untuk:
     - Memberikan gravitasi
     - Menggerakkan player dan pipa
     - Mengecek tabrakan
     - Mengecek skor

4. **Event Permainan**
   - Player menyentuh tanah atau pipa → **Game Over**
   - Skor bertambah setiap berhasil melewati pipa

5. **Saat Game Over**
   - Tampil pesan:
     - `Score : X`
     - `Press R to Restart`
     - `Press M to Return to Menu`

6. **Restart / Exit**
   - Tombol `R` untuk reset permainan (`resetGame()`)
   - Tombol `M` kembali ke menu (`returnToMenu()`)

---

## 📸 Dokumentasi

https://github.com/MamangPermen/TP6DPBO2425C1/blob/main/Documentation/SRecord.mp4
