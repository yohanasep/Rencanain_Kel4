# Rencanain

## Info

Nama aplikasi: Rencanain
Tim pengembang: Kelompok 4

- Frengky Saputra- 221402021  
  Role : Analis, UI/UX Designer dan Front End(Home)

- Clinton Christovel Simanullang- 221402043  
  Role : Back-End (Membuat API CRUD Workspace, CRUD Annoucement dan Invite member workspace)

- Yohana Septamia - 221402056
  Role : Project Manager & Front End (Project List dan Project Detail dan Subtask), Menghubungkan API Register, Detail Workspace, dan Announcement

- Melia Purnamasari Sihombing - 221402112
  Role : Back-End (Membuat API Personal Task, Login dan Register), Menghubungkan API Login, Personal Schedule List & Detail, dan Workspace List

- Muhammad Ahsanul Kholiqin Lubis - 221402115
  Role : UI/UX Designer, Front End(Profile) & Tester

- Yeni Aulia Sinaga - 221402138
  Role : UI/UX Designer, Front End(Login dan Register) & Dokumenter

## Desc

Rencanain adalah aplikasi planner yang kami kembangkan untuk membantu pengguna membuat rencana kegiatan agar menjadi lebih teratur. Aplikasi yang kami kembangkan ini, dapat membuat daftar kegiatan, menetapkan waktu untuk setiap kegiatan, dan menerima pengingat/notifikasi saat jadwal kegiatan sudah dekat. Dengan fitur di atas, diharapkan pengguna dapat lebih aware terhadap jadwal setiap kegiatan dalam proyek, pertemuan penting, maupun waktu pribadi. Dengan Rencanain, pengguna memiliki kesempatan untuk lebih produktif dan terorganisir.


## Features yang sudah selesai
Login,  Register & Logout
Menampilkan daftar schedule
Menampilkan data workspace
Menampilkan profile
Menambah schedule baru
Menambah workspace
Menginvite anggota workspace



## Features yang masih dalam tahap pengembangan

#### Home
- Menandai tugas yang telah selesai
- Reminder

### Admin Workspace
- Mengelola keanggotaan workspace
- Membagi tugas kepada anggota workspace
- Mengelola announcement pada workspace
- Melihat history perubahan pada workspace

### Anggota Workspace
- Memposting dan mencari pada forum
- Melihat history perubahan pada workspace

## Library
Berikut adalah fungsi dari masing-masing pustaka:

1. Gson

Gson adalah pustaka dari Google untuk mengkonversi objek Java menjadi JSON dan sebaliknya.

Fungsi Utama:

Mengubah objek Java ke JSON (Serialization).
Mengubah JSON ke objek Java (Deserialization).


Contoh Penggunaan:

Gson gson = new Gson();
String json = gson.toJson(myObject); // Serialisasi
MyClass myObject = gson.fromJson(json, MyClass.class); // Deserialisasi


2. Retrofit2

Retrofit adalah pustaka HTTP client untuk Android yang mempermudah komunikasi dengan API.

Fungsi Utama:

Mengatur panggilan API secara deklaratif menggunakan interface.
Mendukung berbagai format data (JSON, XML).
Dapat bekerja dengan Gson untuk parsing otomatis.


Contoh Penggunaan:

Retrofit retrofit = new Retrofit.Builder()
.baseUrl("https://api.example.com/")
.addConverterFactory(GsonConverterFactory.create())
.build();

MyApi api = retrofit.create(MyApi.class);
Call<MyResponse> call = api.getData();


3. OkHttp3

OkHttp adalah pustaka HTTP client yang sering digunakan oleh Retrofit sebagai underlying library.

Fungsi Utama:

Mengirim dan menerima permintaan HTTP.
Mendukung fitur seperti caching, interceptors, dan koneksi asinkron.


Contoh Penggunaan:

OkHttpClient client = new OkHttpClient();
Request request = new Request.Builder()
.url("https://api.example.com/data")
.build();

client.newCall(request).enqueue(new Callback() {
@Override
public void onResponse(Call call, Response response) throws IOException {
String responseData = response.body().string();
}

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
    }
});

Integrasi Ketiganya

Ketiga pustaka ini sering digunakan bersama-sama untuk membangun aplikasi Android yang terhubung dengan API. Contohnya:

1. OkHttp menangani koneksi HTTP.


2. Retrofit mempermudah pengelolaan API dengan membuat interface.


3. Gson mempermudah serialisasi/deserialisasi JSON ke objek Java


## Permission
INTERNET Deskripsi : Memberikan izin kepada aplikasi untuk mengakses jaringan internet. Izin ini penting jika aplikasi ingin mengakses sumber daya online, seperti API, server, atau website.

## Environment

Beberapa syarat environment untuk menjalankan aplikasi ini:

- **Operating System**: Windows 11 Professional  
  RAM : 8GB  
  Harddisk : SSD 512GB  
  Processor : Inter Core i5  
  VGA : Intel® Iris® Xᵉ Graphics
- **Kotlin**: Versi terbaru (>= 1.6.0)
- **Android Studio**: Versi terbaru dengan Android SDK 31 atau lebih tinggi.
- **JDK**: Java Development Kit versi 11.
- **Gradle**: Versi yang sesuai dengan Android Studio terbaru.

## Design
Link Figma : https://www.figma.com/design/CUL1hr2bRXVuySqB5KK0ZF/Pemmob?node-id=0-1&t=LBzhnV2S6WbHMc0D-1

### Instalasi dan Setup

1. **Install Android Studio**
    - Download dan install Android Studio dari [Android Studio Official Website](https://developer.android.com/studio).
    - Pastikan Android SDK 31 atau lebih tinggi terinstal melalui SDK Manager.
2. **Install JDK**
    - Download dan install JDK versi 11 dari [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) atau [OpenJDK](https://openjdk.java.net/install/).
3. **Clone Repository**
    - Clone repository ini ke lokal komputer:
      ```
      git clone https://github.com/kelompok4/rencanain.git
      ```
      ```
      git clone https://github.com/MeliaPrnmsr/RencanainAPI.git
      ```
4. **Setup database**
    - Pastikan Anda memiliki MySQL atau MariaDB terinstal.
    - Buka MySQL/MariaDB dan buat database baru untuk aplikasi backend
      ```
      CREATE DATABASE rencanain;
      ```
5. **Konfigurasi Backend**
     - Buka folder RencanainAPI pada IDE yang digunakan dan buka terminal
     ```
     cp .env.example .env
     ```
     - Atur variabel berikut pada file .env
     ```
     DB_CONNECTION=mysql
     DB_HOST=127.0.0.1
     DB_PORT=3306
     DB_DATABASE=rencanain
     DB_USERNAME=[username]
     DB_PASSWORD=[password]
     ```
     - Jalankan perintah berikut untuk menginstal dependensi Laravel
     ```
     composer install
     ```
     - Generate key
     ```
     php artisan key:generate
     ```
     - Jalankan migrasi database
     ```
     php artisan migrate --seed
     ```
      - Cek IP kamu pada terminal
     ```
     ipconfig
     ```
     - Jalankan server backend dengan host yang disesuaikan dengan IP kamu
     ```
     php artisan serve --host=ip_kamu --port=8000
     ```
6. **Setup Frontend**
     - Buka Android Studio dan pilih opsi Open Project.
     - Arahkan ke folder rencanain dan buka proyek.
     - Tunggu hingga semua dependensi terinstal otomatis melalui Gradle.
     - Setup Retro
       - Buka file Retro.kt pada app/src/main/java/komc/kel4/rencanain/api/Retro.kt
       - ubah baseUrl sesuai dengan IP kamu
     ```
      .baseUrl("http://ip_kamu:8000/api/")
     ```
     - Jalankan aplikasi di emulator atau perangkat fisik
     - Pilih perangkat target di Android Studio.
     - Klik tombol Run (ikon segitiga hijau) di toolbar.
