package com.bigdatapassion.kinesis;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;
import software.amazon.awssdk.services.kinesis.model.PutRecordResponse;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class KinesisProducerExample {

    public static void main(String[] args) throws InterruptedException {

        KinesisConfiguration kinesisConfiguration = new KinesisConfiguration();
        kinesisConfiguration.load();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        KinesisClient kinesisClient = KinesisClient.builder()
                .region(kinesisConfiguration.getRegion())
                .build();

        for (int j = 0; j < 1000; j++) {

            String partitionKey = String.format("partitionKey-%d", j);
            String currentDate = LocalDateTime.now().format(formatter);
            String message = "To jest testowa wiadomość o kluczu: " + partitionKey + "z dnia " + currentDate;

            PutRecordRequest kinesisRecord = PutRecordRequest.builder()
                    .streamName(kinesisConfiguration.getStreamName())
                    .data(SdkBytes.fromString(message, StandardCharsets.UTF_8))
                    .partitionKey(partitionKey)
                    .build();

            PutRecordResponse putRecordResponse = kinesisClient.putRecord(kinesisRecord);

            System.out.println(MessageFormat.format("Wysłano wiadomość do shard {0} o treści: {1}", putRecordResponse.shardId(), message));

            Thread.sleep(1000);
        }
    }

}
