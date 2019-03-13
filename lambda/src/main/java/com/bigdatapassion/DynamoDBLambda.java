package com.bigdatapassion;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.bigdatapassion.dto.RequestClass;
import com.bigdatapassion.dto.ResponseClass;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class DynamoDBLambda implements RequestHandler<RequestClass, ResponseClass> {

    private static final String TABLE_NAME = "lambda-hello";

    public ResponseClass handleRequest(RequestClass request, Context context) {

        String greetingString = String.format("Hello %s, %s.", request.getFirstName(), request.getLastName());

        context.getLogger().log(greetingString);

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("Timestamp", new AttributeValue().withN("" + Instant.now().getEpochSecond()));
        item.put("FirstName", new AttributeValue().withS(request.getFirstName()));
        item.put("LastName", new AttributeValue().withS(request.getLastName()));

        AmazonDynamoDB dynamoClient = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        dynamoClient.putItem(TABLE_NAME, item);
        dynamoClient.shutdown();

        return new ResponseClass(greetingString);
    }

}