#!/usr/bin/env bash

aws lambda delete-function --function-name lambda-function-in-java-simple
aws lambda delete-function --function-name lambda-function-in-java-dynamodb
aws lambda delete-function --function-name lambda-function-in-java-hello
aws lambda delete-function --function-name lambda-function-in-java-stream
