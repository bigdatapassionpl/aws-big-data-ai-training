#!/usr/bin/env bash


# Add Lambda to SNS as subscription
aws sns subscribe \
    --topic-arn arn:aws:sns:us-east-1:020214251602:PolitechnikaSnsTopic \
    --protocol lambda \
    --notification-endpoint arn:aws:lambda:us-east-1:252594130684:function:lambda-function-in-python-sns-fetch

# Give permissions to Lambda to access that subscription i.e. Add it through triggers
aws lambda add-permission \
    --function-name lambda-function-in-python-sns-fetch \
    --statement-id 276xxxxxx\
    --action "lambda:InvokeFunction" \
    --principal sns.amazonaws.com \
    --source-arn arn:aws:sns:us-east-1:020214251602:PolitechnikaSnsTopic

# Send message to publish and trigger lamda
aws sns publish \
    --topic-arn arn:aws:sns:us-east-1:020214251602:PolitechnikaSnsTopic \
    --subject "HelloFromSNSTopicAndLambda" \
    --message "Testowa wiadomość z konsoli do AWS SNS"