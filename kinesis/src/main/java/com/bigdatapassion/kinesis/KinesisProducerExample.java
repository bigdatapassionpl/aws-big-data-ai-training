package com.bigdatapassion.kinesis;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class KinesisProducerExample {

    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

//        BasicAWSCredentials awsCreds = new BasicAWSCredentials("access_key_id", "secret_key_id");

        Region region = Region.US_EAST_1;
        KinesisClient kinesisClient = KinesisClient.builder()
//                .credentialsProvider()
                .region(region)
                .build();

        for (int j = 0; j < 1000; j++) {

            String message = "To jest testowa wiadomość z dnia " + LocalDateTime.now().format(formatter);
            PutRecordRequest kinesisRecord = PutRecordRequest.builder()
                    .streamName("pw-test-1o89824")
                    .data(SdkBytes.fromString(message, StandardCharsets.UTF_8))
                    .partitionKey(String.format("partitionKey-%d", j / 5))
                    .build();

            kinesisClient.putRecord(kinesisRecord);
        }
    }

}
