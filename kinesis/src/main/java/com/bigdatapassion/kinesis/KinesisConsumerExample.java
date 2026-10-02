package com.bigdatapassion.kinesis;

import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KinesisConsumerExample {

    public static void main(String[] args) throws Exception {

        KinesisConfiguration kinesisConfiguration = new KinesisConfiguration();
        kinesisConfiguration.load();

        String streamName = args.length > 0 && !args[0].isBlank() ? args[0] : kinesisConfiguration.getStreamName();
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
