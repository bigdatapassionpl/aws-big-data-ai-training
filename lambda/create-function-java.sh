#!/usr/bin/env bash

mvn clean install

aws lambda create-function \
    --function-name lambda-function-in-java \
    --runtime java8 \
    --role arn:aws:iam::252594130684:role/Politechnika  \
    --handler com.bigdatapassion.DynamoDBLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar

echo -e "\nFunctions:"
aws lambda list-functions