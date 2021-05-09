package com.bigdatapassion.kinesis;

import com.google.gson.Gson;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;
import software.amazon.awssdk.services.kinesis.model.PutRecordResponse;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

public class KinesisProducerExample {

    public static void main(String[] args) throws InterruptedException {

        Gson gson = new Gson();
        MessageFactory personFactory = new MessageFactory();

        KinesisConfiguration kinesisConfiguration = new KinesisConfiguration();
        kinesisConfiguration.load();

        AwsCredentialsProvider credentialsProvider = kinesisConfiguration.createCredentialsProvider();

        KinesisClient kinesisClient = KinesisClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(kinesisConfiguration.getRegion())
                .build();

        for (int j = 0; j < 1000; j++) {

            Message message = personFactory.generateNextMessage(j);
            String jsonMessage = gson.toJson(message);

            PutRecordRequest kinesisRecord = PutRecordRequest.builder()
                    .streamName(kinesisConfiguration.getStreamName())
                    .data(SdkBytes.fromString(jsonMessage, StandardCharsets.UTF_8))
                    .partitionKey(message.getPartitionKey())
                    .build();

            PutRecordResponse putRecordResponse = kinesisClient.putRecord(kinesisRecord);

            System.out.println(MessageFormat.format("Wysłano wiadomość do shard {0} o treści: {1}", putRecordResponse.shardId(), jsonMessage));

            Thread.sleep(500);
        }
    }

}
