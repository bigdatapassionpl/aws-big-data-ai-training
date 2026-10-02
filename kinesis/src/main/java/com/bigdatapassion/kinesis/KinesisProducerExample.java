package com.bigdatapassion.kinesis;

import com.google.gson.Gson;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordsRequest;
import software.amazon.awssdk.services.kinesis.model.PutRecordsRequestEntry;
import software.amazon.awssdk.services.kinesis.model.PutRecordsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

@Command(name = "kinesis-producer", sortOptions = false, description = "Sends generated JSON messages to a Kinesis stream.")
public class KinesisProducerExample implements Callable<Integer> {

    @Option(names = {"-n", "--messages"}, paramLabel = "<count>", defaultValue = "1000", description = "Number of messages (default: ${DEFAULT-VALUE})")
    private int totalMessages;

    @Option(names = {"-b", "--batch-size"}, paramLabel = "<size>", defaultValue = "1", description = "Batch size, 1-500 (default: ${DEFAULT-VALUE})")
    private int batchSize;

    @Option(names = {"-t", "--sleep-ms"}, paramLabel = "<ms>", defaultValue = "500", description = "Sleep time between batches in ms (default: ${DEFAULT-VALUE})")
    private long sleepTimeMs;

    @Option(names = {"-s", "--stream"}, paramLabel = "<name>", description = "Kinesis stream name (default: kinesis.properties)")
    private String streamName;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Show this help message and exit")
    private boolean help;

    public static void main(String[] args) {
        System.exit(new CommandLine(new KinesisProducerExample()).execute(args));
    }

    @Override
    public Integer call() throws InterruptedException {

        Gson gson = new Gson();
        MessageFactory personFactory = new MessageFactory();

        KinesisConfiguration kinesisConfiguration = new KinesisConfiguration();
        kinesisConfiguration.load();

        AwsCredentialsProvider credentialsProvider = kinesisConfiguration.createCredentialsProvider();

        KinesisClient kinesisClient = KinesisClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(kinesisConfiguration.getRegion())
                .build();

        batchSize = Math.max(1, Math.min(500, batchSize)); // Kinesis limit: 1-500 records
        if (streamName == null || streamName.isBlank()) {
            streamName = kinesisConfiguration.getStreamName();
        }
        System.out.printf("Messages: %d, batch size: %d, sleep: %d ms, Kinesis stream: %s%n",
                totalMessages, batchSize, sleepTimeMs, streamName);

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
                    .streamName(streamName)
                    .records(entries)
                    .build();

            PutRecordsResponse putRecordsResponse = kinesisClient.putRecords(putRecordsRequest);
            sentMessages += currentBatchSize;

            System.out.println(MessageFormat.format("Sent a batch of {0} messages. Total sent: {1}/{2}. Failed: {3}",
                    currentBatchSize, sentMessages, totalMessages, putRecordsResponse.failedRecordCount()));

            if (sentMessages < totalMessages) {
                Thread.sleep(sleepTimeMs);
            }
        }
        return 0;
    }

}
