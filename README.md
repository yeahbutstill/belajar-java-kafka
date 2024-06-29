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

