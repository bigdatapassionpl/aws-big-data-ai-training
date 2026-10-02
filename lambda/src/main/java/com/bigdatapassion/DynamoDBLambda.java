package com.bigdatapassion;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.bigdatapassion.dto.RequestClass;
import com.bigdatapassion.dto.ResponseClass;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class DynamoDBLambda implements RequestHandler<RequestClass, ResponseClass> {

    private static final String TABLE_NAME = "lambda-hello";

    public ResponseClass handleRequest(RequestClass request, Context context) {

        String greetingString = String.format("Hello %s, %s.", request.getFirstName(), request.getLastName());

        context.getLogger().log(greetingString);

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("Timestamp", AttributeValue.builder().n("" + Instant.now().getEpochSecond()).build());
        item.put("FirstName", AttributeValue.builder().s(request.getFirstName()).build());
        item.put("LastName", AttributeValue.builder().s(request.getLastName()).build());

        DynamoDbClient dynamoClient = DynamoDbClient.builder().region(Region.US_EAST_1).build();
        dynamoClient.putItem(PutItemRequest.builder().tableName(TABLE_NAME).item(item).build());
        dynamoClient.close();

        return new ResponseClass(greetingString);
    }

}