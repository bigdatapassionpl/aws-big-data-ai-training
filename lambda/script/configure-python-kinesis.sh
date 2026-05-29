#!/usr/bin/env bash

export AWS_KINESIS_STREAM_ARN="arn:aws:kinesis:us-east-1:595221284641:stream/kinesis-data-stream-example"
export AWS_LAMBDA_NAME="lambda_kinesis_reader"

# Add Lambda to Kinesis as event source mapping
aws lambda create-event-source-mapping \
    --event-source-arn $AWS_KINESIS_STREAM_ARN \
    --function-name $AWS_LAMBDA_NAME \
    --starting-position LATEST \
    --batch-size 100
