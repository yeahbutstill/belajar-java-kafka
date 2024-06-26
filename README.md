# Topic
- Saat kita mengirim data ke Kafka, kita harus mengirim data tersebut ke Topic 
- Topic itu mirip Table di Database, dimana digunakan untuk menyimpan data yang dikirim oleh pengirim data

# Diagram Topic
![Diagram Topic](pic/img.png)

# Log
- Data di topic disimpan dalam format Log 
- Apa itu Log? Log adalah cara menyimpan yang paling sederhana, yaitu append-only (hanya bertambah), berurutan sesuai dengan urutan data masuk 
- Setiap data kita simpan di dalam topic, maka data akan bertambah pada urutan terakhir 
- Hal ini membuat proses penyimpanan data ke Kafka sangat cepat, karena hanya menyimpan data di bagian akhir saja

# Diagram Topic Log
![Diagram Topic](pic/img_1.png)

# Message 
- Data yang kita kirim ke topic di Kafka kita sebut Message (kalau di database biasanya kita sebut baris data atau row data atau record data)
- Message merupakan data dengan struktur yang sudah ditentukan oleh Kafka 
- Walaupun kenyataannya mungkin jarang digunakan, namun kita perlu tahu informasi yang terdapat di Message

# Struktur Message
- Topic adalah nama topic untuk menyimpan message 
- Partition adalah nomor partisi untuk menyimpan message 
- Header adalah informasi tambahan untuk message 
- Key adalah id untuk message, Key ini bukan seperti Primary Key di database, Key di Kafka boleh sama antar Message 
- Value adalah isi data untuk message

![Struktur Message](pic/img_2.png)

# Producer
- Producer adalah pihak yang mengirim Message ke Kafka 
- Contoh pada kasus sebelumnya, aplikasi Order adalah Producer untuk Message Order 
- Kafka sendiri menyediakan aplikasi sederhana berbasis terminal untuk Producer, namun aplikasi ini hanya sederhana 
- Untuk praktek yang lebih kompleks, kita akan coba simulasikan di bagian akhir menggunakan bahasa pemrograman Java
- Perlu diingat, setiap mengirim Message ke Kafka, maka akan disimpan di urutan paling akhir

# Consumer
- Consumer adalah aplikasi yang membaca/menerima data dari Kafka 
- Pada kasus sebelumnya, aplikasi Logistic dan Payment adalah Consumer untuk Message Order 
- Membaca data dari Kafka akan dilakukan secara berurutan dari nomor Message paling awal sampai paling akhir

# Diagram PUB/SUB
- ![PUB/SUB](pic/img_3.png)

# Publish Subscribe
- Ketika kita mengirim lagi data ke Topic yang sedang dibaca oleh Consumer, secara otomatis data akan dibaca oleh Consumer 
- Sehingga kita tidak perlu menjalankan ulang aplikasi Consumer dari awal lagi

# Consumer Group
- Saat Consumer membaca data dari Topic, maka Consumer permu menentukan Consumer Group yang digunakan 
- Sebelumnya kita tidak menyebutkan Consumer Group yang kita gunakan, yang jika tidak disebutkan, secara otomatis akan dibuatkan baru oleh Kafka 
- Namun pada kenyataannya saat membuat aplikasi, Consumer Group pasti akan selalu kita sebutkan 
- Biasanya Consumer Group kebanyakan menggunakan nama aplikasi yang menjadi Consumer nya

# Tanpa Menyebutkan Consumer Group
- Jika kita tidak menyebutkan Consumer Group, secara otomatis akan dibuat Consumer Group baru 
- Dan saat membaca data dari Topic, Kafka hanya akan memberikan data ke Consumer dengan Consumer Group secara unik, artinya tidak akan diberikan dua kali ke Consumer yang menggunakan Consumer Group yang sama 
- Jika Consumer Group selalu berbeda-beda, maka secara otomatis data akan diterima berkali-kali 
- Kita liat contohnya, misal aplikasi Payment akan menjadi Consumer dari Message Order, namun Payment tidak menggunakan Consumer Group

# Tanpa Consumer Group
![Tanpa Consumer Group](pic/img_4.png)

# Menggunakan Consumer Group
- Jika Consumer menggunakan Consumer Group, maka Consumer-Consumer yang menggunakan Consumer Group yang sama akan dianggap satu kesatuan 
- Oleh karena itu, data tidak akan dikirim berkali-kali ke semua Consumer, melainkan hanya sekali ke Consumer Group (Consumer akan dipilih dari Consumer Group yang sama)
- Dengan begitu, kita tidak akan menerima data berkali-kali

# Menggunakan Consumer Group
![Menggunakan Consumer Group](pic/img_5.png)

# Latihan
- Silahkan coba jalankan lebih dari satu Consumer dengan Consumer Group sama, dan kirim Message ke topic tersebut. Pastikan hanya satu Consumer yang mendapatkan data 
- Coba matikan Consumer yang sebelumnya menerima data, lalu coba kirim lagi data baru. Pastikan Consumer lainnya secara otomatis mendapatkan data baru tersebut

# Ketika Tidak Ada Consumer
- Sebelumnya kita sudah tahu bahwa data di Topic, disimpan secara berurutan, dimulai dari nomor 0 sampai seterusnya 
- Jika misal kita menghentikan semua Consumer, namun Producer tetap mengirim data, pertanyaannya, ketika nanti Consumer berjalan lagi, dari mana Consumer akan membaca data? Apakah dari awal lagi? Atau mulai dari data terbaru saja? Atau dari data terakhir ketika Consumer dihentikan?

# Cara Menentukan Data yang Dibaca Consumer
- Secara default, Consumer akan mulai membaca data baru yang masuk. Seandainya misal di Topic sudah ada 10 data, lalu kita pertama kali menjalankan Consumer, maka Consumer akan mulai membaca data dari data ke 11 dan selanjutnya 
- Saat kita menggunakan --from-beginning, maka Consumer akan membaca data dari awal, yaitu dari data ke-1 atau dari index 0
- Lantas bagaimana jika misal Consumer sudah selesai membaca sampai data ke-5, lalu Consumer dihentikan? Saat dijalankan ulang, Consumer tidak akan membaca dari ke-1 lagi (awal), atau dari ke-11 (baru), melainkan dari terakhir Consumer dimatikan, yaitu ke-5, yang artinya akan mulai membaca dari ke-6 
- Pertanyaannya bagaimana Consumer tahu bahwa terakhir data yang sudah dibaca adalah data ke-5?

# Offset
- Kafka menyimpan informasi data terakhir yang dibaca dengan sebutan offset 
- Saat pertama kali Consumer berjalan, data Offset tidak ada, oleh karena itu kita harus menentukan mau di awal (--from-beginning) atau mau dari data baru? 
- Namun ketika Consumer berjalan, lalu membaca data, maka Consumer akan menyimpan informasi Offset (data terakhir yang dibaca), dengan begitu ketika aplikasi Consumer dihentikan, lalu dijalankan ulang, maka Consumer bisa mendapatkan informasi Offset terakhir, dan melanjutkan membaca data dari Offset terakhir 
- Informasi Offset disimpan dengan informasi Consumer Group, yang artinya jika kita menjalankan Consumer dengan Consumer Group yang berbeda, maka informasi Offset otomatis akan hilang

# Partition
- Saat kita membuat Topic, Kafka akan menyimpan data Topic tersebut dalam Partition. Dan jumlah Partition secara default adalah 1 
- Partition di Kafka, hanya bisa dibaca oleh 1 Consumer, disinilah jawaban kenapa data yang kita kirim di awal selalu diterima oleh 1 Consumer, tidak pernah berpindah ke Consumer yang lain, sampai Consumer tersebut dihentikan, baru data akan berpindah ke Consumer lainnya 
- Kafka, bisa menyimpan data Topic dalam beberapa Partisi 
- Dengan begitu, kita bisa membuat beberapa Consumer bekerja, karena ada cukup Partisi yang bisa dibaca oleh Consumer 
- Kita akan coba simulasikan bagaimana Consumer bekerja ketika jumlah Partisi ada banyak

![Partition](pic/img_6.png)
![Partition](pic/img_7.png)
![Partition](pic/img_8.png)
![Partition](pic/img_9.png)
![Partition](pic/img_10.png)
![Partition](pic/img_11.png)

# Menambah Partition
- Secara default, saat kita membuat Topic, maka jumlah Partition hanya 1 
- Kita bisa menentukan jumlah Partition saat membuat Topic, atau mengubah jumlah Partition di Topic yang sudah kita buat 
- Untuk membuat Topic dengan Partition :
  kafka-topics.sh --bootstrap-server <connection-string>:<port> --create --topic <string> --partitions <number>
- Untuk mengubah Topic yang sudah dibuat :
  kafka-topics.sh --bootstrap-server <connection-string>:<port> --alter --topic <string> --partitions <number>
- Untuk melihat detail Topic yang sudah dibuat :
  kafka-topics.sh --bootstrap-server <connection-string>:<port> --describe --topic <string>

# Latihan 
- Jalankan 3 Consumer ke Topic helloworld, lalu coba kirim data ke Topic helloworld 
- Coba matikan satu persatu Consumer, dan sisakan 1 Consumer, sambil mengirim data ke Topic helloworld 
- Cek lagi Offset untuk Consumer Group yang digunakan

# Memilih Partition
- Saat kita mengirim data ke Topic, bagaimana cara menentukan Partition mana yang akan digunakan untuk menyimpan data? 
- Contoh, kenapa sebelumnya saat kita mengirim data, selalu masuk ke Partition yang sama? Tidak pernah berubah? 
- Jawabannya adalah penentuan Partition, ditentukan oleh Key pada Message yang kita kirim 
- Sebelumnya, saat kita mengirim data menggunakan Console Producer, Key yang digunakan adalah null (kosong), secara otomatis akan selalu dikirim ke Partition yang sama
 
# Key Routing
- Key di Kafka Message tidak seperti Primary Key di Database, Key di Kafka Message salah satunya digunakan untuk menentukan Partition yang dipilih 
- Saat menentukan Partition mana yang akan dipilih, Kafka akan menggunakan rumus :
  hash(message.key) % total partition 
- Misal ketika kita mengirim Message dengan key “eko”, dengan jumlah Partition 2, maka, Partition yang dipilih adalah:
  hash(eko) % 2 
- Misal saja, hasil perhitungan hash(eko) = 8, artinya :
  8 % 2 = 0 
- Artinya Message akan disimpan di Partition 0 
- Oleh karena itu, Key yang sama, pasti akan selalu masuk ke Partition yang sama

# Console Producer
- Defaultnya, Console Producer akan menggunakan Key null (kosong) ketika mengirim data. 
- Jika kita ingin menentukan Key, kita bisa tambahkan parameter --property "parse.key=true" --property "key.separator=:"

# Console Consumer
- Untuk melihat Key yang ada di Message, di Console Consumer, kita bisa tambahkan perintah --property “print.key=true”

# Kafka Client
- Sebelumnya, kita sudah mencoba membuat Producer dan Consumer menggunakan Kafka Console Producer dan Consumer 
- Namun pada kenyataanya, saat kita membuat aplikasi, kita harus membuat Producer dan Consumer langsung di aplikasi yang kita buat 
- Untungnya, ekosistem Kafka sudah sangat matang dan besar, sehingga kita tidak akan kesulitan ketika menggunakan Kafka 
- Di materi-materi selanjutnya, kita akan coba menggunakan teknologi Golang, NodeJS dan Java untuk membuat Producer dan Consumer Kafka
