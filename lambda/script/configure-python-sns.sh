#!/usr/bin/env bash

export AWS_SNS_TOPIC_ARN="arn:aws:sns:us-east-1:012235137533:PolitechnikaSNSTopic"
export AWS_LAMBDA_ARN="arn:aws:lambda:us-east-1:932771310344:function:lambda_sns_reader"

# Add Lambda to SNS as subscription
aws sns subscribe \
    --topic-arn $AWS_SNS_TOPIC_ARN \
    --protocol lambda \
    --notification-endpoint $AWS_LAMBDA_ARN

# Give permissions to Lambda to access that subscription i.e. Add it through triggers
aws lambda add-permission \
    --function-name lambda_sns_reader \
    --statement-id 276xxxxxx\
    --action "lambda:InvokeFunction" \
    --principal sns.amazonaws.com \
    --source-arn $AWS_SNS_TOPIC_ARN

# Send message to publish and trigger lamda
aws sns publish \
    --topic-arn $AWS_SNS_TOPIC_ARN \
    --subject "HelloFromSNSTopicAndLambda" \
    --message "Testowa wiadomość z konsoli do AWS SNS"
