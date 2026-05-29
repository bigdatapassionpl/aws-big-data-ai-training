package com.bigdatapassion.kinesis;

import com.google.gson.Gson;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordsRequest;
import software.amazon.awssdk.services.kinesis.model.PutRecordsRequestEntry;
import software.amazon.awssdk.services.kinesis.model.PutRecordsResponse;

import java.util.ArrayList;
import java.util.List;

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

        int totalMessages = 1000;
        int batchSize = 1;
        long sleepTimeMs = 500;

        if (args.length > 0) {
            try {
                totalMessages = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Niepoprawny format parametru liczby wiadomości. Użyto wartości domyślnej: " + totalMessages);
            }
        }
        if (args.length > 1) {
            try {
                batchSize = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Niepoprawny format parametru rozmiaru paczki. Użyto wartości domyślnej: " + batchSize);
            }
        }
        if (args.length > 2) {
            try {
                sleepTimeMs = Long.parseLong(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("Niepoprawny format parametru czasu oczekiwania. Użyto wartości domyślnej: " + sleepTimeMs);
            }
        }

        int sentMessages = 0;
        while (sentMessages < totalMessages) {
            int currentBatchSize = Math.min(batchSize, totalMessages - sentMessages);
            List<PutRecordsRequestEntry> entries = new ArrayList<>();

            for (int i = 0; i < currentBatchSize; i++) {
                Message message = personFactory.generateNextMessage(sentMessages + i);
                String jsonMessage = gson.toJson(message);

                PutRecordsRequestEntry entry = PutRecordsRequestEntry.builder()
                        .partitionKey(message.getPartitionkey())
                        .data(SdkBytes.fromString(jsonMessage, StandardCharsets.UTF_8))
                        .build();
                entries.add(entry);
            }

            PutRecordsRequest putRecordsRequest = PutRecordsRequest.builder()
                    .streamName(kinesisConfiguration.getStreamName())
                    .records(entries)
                    .build();

            PutRecordsResponse putRecordsResponse = kinesisClient.putRecords(putRecordsRequest);
            sentMessages += currentBatchSize;

            System.out.println(MessageFormat.format("Wysłano paczkę {0} wiadomości. Łącznie wysłano: {1}/{2}. Błędy: {3}",
                    currentBatchSize, sentMessages, totalMessages, putRecordsResponse.failedRecordCount()));

            if (sentMessages < totalMessages) {
                Thread.sleep(sleepTimeMs);
            }
        }
    }

}
