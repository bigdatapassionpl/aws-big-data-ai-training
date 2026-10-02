package com.bigdatapassion.kinesis;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Command(name = "kinesis-consumer", description = "Reads all messages from a Kinesis stream and prints them.")
public class KinesisConsumerExample implements Callable<Integer> {

    @Option(names = {"-s", "--stream"}, paramLabel = "<name>", description = "Kinesis stream name (default: kinesis.properties)")
    private String streamName;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Show this help message and exit")
    private boolean help;

    public static void main(String[] args) {
        System.exit(new CommandLine(new KinesisConsumerExample()).execute(args));
    }

    @Override
    public Integer call() throws InterruptedException {

        KinesisConfiguration kinesisConfiguration = new KinesisConfiguration();
        kinesisConfiguration.load();

        if (streamName == null || streamName.isBlank()) {
            streamName = kinesisConfiguration.getStreamName();
        }
        System.out.println("Kinesis stream: " + streamName);

        KinesisClient kinesisClient = KinesisClient.builder()
                .region(kinesisConfiguration.getRegion())
                .build();

        DescribeStreamRequest describeRequest = DescribeStreamRequest.builder()
                .streamName(streamName)
                .build();

        DescribeStreamResponse describeResponse = kinesisClient.describeStream(describeRequest);
        List<Shard> shards = describeResponse.streamDescription().shards();

        // https://docs.aws.amazon.com/kinesis/latest/APIReference/API_StartingPosition.html

        Map<String, String> shardsIterators = shards.stream()
                .collect(Collectors.toMap(
                        Shard::shardId,
                        shard -> getFirstIteratorForShard(streamName, kinesisClient, shard)
                ));


        while (true) {

            shardsIterators = shardsIterators.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> processNextShardMessages(kinesisClient, entry.getKey(), entry.getValue())
                    ));

            // waiting for next get records due to limitations
            Thread.sleep(1000);
        }

    }

    private static String getFirstIteratorForShard(String streamName, KinesisClient kinesisClient, Shard shard) {

        GetShardIteratorRequest iteratorRequest = GetShardIteratorRequest.builder()
                .streamName(streamName)
                .shardId(shard.shardId())
                .shardIteratorType(ShardIteratorType.TRIM_HORIZON)
                .build();

        return kinesisClient.getShardIterator(iteratorRequest).shardIterator();
    }

    /**
     * as simple as possible, display only
     */
    private static String processNextShardMessages(KinesisClient kinesisClient, String shardId, String shardIterator) {

        GetRecordsRequest kinesisRequest = GetRecordsRequest.builder()
                .shardIterator(shardIterator)
                .build();

        GetRecordsResponse recordsResponse = kinesisClient.getRecords(kinesisRequest);

        if (recordsResponse.hasRecords()) {
            recordsResponse.records().forEach(record -> System.out.println(shardId + " -> " + record.data().asString(StandardCharsets.UTF_8)));
        }

        return recordsResponse.nextShardIterator();
    }

}
