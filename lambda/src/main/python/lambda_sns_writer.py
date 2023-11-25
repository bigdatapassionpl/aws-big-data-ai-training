import json
import boto3
import random

sns_topic_arn = 'arn:aws:sns:us-east-1:317055048278:PolitechnikaSNSTopic'

def lambda_handler(event, context):

    random_number = random.randint(1, 100)

    data_to_publish = {
        'message': 'Hello from Lambda!',
        'number': random_number
    }

    sns_client = boto3.client('sns')

    response = sns_client.publish(
        TopicArn=sns_topic_arn,
        Message=json.dumps(data_to_publish),
        Subject='Lambda Notification',
    )

    print(f"MessageId: {response['MessageId']}")

    return {
        'statusCode': 200,
        'body': json.dumps(f'Message {random_number} published to SNS successfully!')
    }
