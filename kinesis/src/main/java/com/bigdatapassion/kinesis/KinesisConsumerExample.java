package com.bigdatapassion.kinesis;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class KinesisConsumerExample {

    public static void main(String[] args) throws InterruptedException {

        Region region = Region.US_EAST_1;
        KinesisClient kinesisClient = KinesisClient.builder()
//                .credentialsProvider()
                .region(region)
                .build();

        DescribeStreamRequest describeRequest = DescribeStreamRequest.builder()
                .streamName("pw-test-1o89824")
                .build();

        DescribeStreamResponse describeResponse = kinesisClient.describeStream(describeRequest);
        List<Shard> shards = describeResponse.streamDescription().shards();

        // https://docs.aws.amazon.com/kinesis/latest/APIReference/API_StartingPosition.html
        Shard shard = shards.get(0);
        GetShardIteratorRequest iteratorRequest = GetShardIteratorRequest.builder()
                .streamName("pw-test-1o89824")
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
