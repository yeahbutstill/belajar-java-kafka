package programmerzamannow.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
public class ProducerApp {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Properties properties = new Properties();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    // nah kalau ini kebalikannya dari tipe data string menjadi tipe data yang ada di kafka
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

      try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {

          for (int i = 0; i < 1_000_000; i++) {
              // topic, key, value
              ProducerRecord<String, String> message = new ProducerRecord<>("kominfoapaansih", Integer.toString(i), "Hello bajingan pemerintah" + i);
              // send ini dia asynchronous(dia tidak ditunggu). Kalau pengen tunggu sampe datanya berhasil dikirim ke kafka maka panggil get
              log.info(String.valueOf(producer.send(message).get()));
//              producer.send(message).get();
          }

      }
  }
}
