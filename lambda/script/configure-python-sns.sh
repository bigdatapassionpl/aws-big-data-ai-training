#!/usr/bin/env bash

export ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
export AWS_SNS_TOPIC_ARN="arn:aws:sns:us-east-1:${ACCOUNT_ID}:PolitechnikaSNSTopic"
export AWS_LAMBDA_NAME="lambda_sns_reader"
export AWS_LAMBDA_ARN="arn:aws:lambda:us-east-1:${ACCOUNT_ID}:function:${AWS_LAMBDA_NAME}"

# Add Lambda to SNS as subscription
aws sns subscribe \
    --topic-arn $AWS_SNS_TOPIC_ARN \
    --protocol lambda \
    --notification-endpoint $AWS_LAMBDA_ARN

# Give permissions to Lambda to access that subscription i.e. Add it through triggers
aws lambda add-permission \
    --function-name $AWS_LAMBDA_NAME \
    --statement-id 276xxxxxx\
    --action "lambda:InvokeFunction" \
    --principal sns.amazonaws.com \
    --source-arn $AWS_SNS_TOPIC_ARN

# Send message to publish and trigger lamda
aws sns publish \
    --topic-arn $AWS_SNS_TOPIC_ARN \
    --subject "HelloFromSNSTopicAndLambda" \
    --message "Test message from the console to AWS SNS"
