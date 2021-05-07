package com.bigdatapassion.kinesis;

import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class KinesisConsumerExample {

    public static void main(String[] args) throws Exception {

        KinesisConfiguration kinesisConfiguration = new KinesisConfiguration();
        kinesisConfiguration.load();

        KinesisClient kinesisClient = KinesisClient.builder()
                .region(kinesisConfiguration.getRegion())
                .build();

        DescribeStreamRequest describeRequest = DescribeStreamRequest.builder()
                .streamName(kinesisConfiguration.getStreamName())
                .build();

        DescribeStreamResponse describeResponse = kinesisClient.describeStream(describeRequest);
        List<Shard> shards = describeResponse.streamDescription().shards();

        // https://docs.aws.amazon.com/kinesis/latest/APIReference/API_StartingPosition.html
        Shard shard = shards.get(0);
        GetShardIteratorRequest iteratorRequest = GetShardIteratorRequest.builder()
                .streamName(kinesisConfiguration.getStreamName())
                .shardId(shard.shardId())
                .shardIteratorType(ShardIteratorType.TRIM_HORIZON)
                .build();

        GetShardIteratorResponse iteratorResponse = kinesisClient.getShardIterator(iteratorRequest);
        String shardIterator = iteratorResponse.shardIterator();

        while (true) {
            System.out.println("Interator: " + shardIterator);

            GetRecordsRequest kinesisRequest = GetRecordsRequest.builder()
                    .shardIterator(shardIterator)
                    .build();

            GetRecordsResponse recordsResponse = kinesisClient.getRecords(kinesisRequest);

            if (recordsResponse.hasRecords()) {
                recordsResponse.records().forEach(record -> System.out.println(record.data().asString(StandardCharsets.UTF_8)));
            }

            shardIterator = recordsResponse.nextShardIterator();

            Thread.sleep(1000);
        }

    }

}
