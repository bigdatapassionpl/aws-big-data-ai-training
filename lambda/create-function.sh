#!/usr/bin/env bash

aws lambda create-function \
    --function-name lambda-function-in-java \
    --runtime java8 \
    --role arn:aws:iam::252594130684:role/Politechnika  \
    --handler com.bigdatapassion.SimpleLambda \
    --timeout 15 \
    --memory-size 512 \
    --zip-file fileb://target/lambda-1.0-SNAPSHOT.jar
