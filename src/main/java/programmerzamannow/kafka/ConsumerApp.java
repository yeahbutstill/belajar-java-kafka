package programmerzamannow.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Slf4j
public class ConsumerApp {

  public static void main(String[] args) {
    Properties properties = new Properties();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // kalau di terminal dia --from-beginning
    // di sini key dan valuenya itu mau menggunakan tipe data apa, di sini kita menggunakan String
      // Deserializer itu mengubah dari data yang ada di kafka menjadi tipe data String
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "ransomware");

    // lalu di sini kita buat kafka consumernya yang tipen key dan valuenya adalah string, sesuai dengan properties consumer config di atas
      try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
          // lalau sub ke kafka topic yang bernama kominfoapaansih
          consumer.subscribe(List.of("kominfoapaansih"));

          // setelah itu ambil datanya dari topic yang sudah di sub di atas. gunakan poll, lalu poll akan mengembalikan consumer record artinya (sekali narik data bisa lebih dari 1)
          while (true) {
              ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1)); // duration.ofsecond selama 1 detik kalau tidak ada data maka timeout
              // iterasi dari consumer record ini dan kita print data valuenya
              for (ConsumerRecord<String, String> message : records) {
                  log.info("Received message: key={}, value={}", message.key(), message.value());
              }
          }
      }
  }
}
