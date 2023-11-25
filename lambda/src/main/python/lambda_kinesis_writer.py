import json
import boto3
import random

kinesis_stream_name = 'kinesis-data-stream-example'

def lambda_handler(event, context):

    random_number = random.randint(1, 100)

    data_to_put = {
        'message': 'Hello from Lambda!',
        'number': random_number
    }

    kinesis_client = boto3.client('kinesis')

    response = kinesis_client.put_record(
        StreamName=kinesis_stream_name,
        Data=json.dumps(data_to_put),
        PartitionKey='1'
    )

    print(f"SequenceNumber: {response['SequenceNumber']}")

    return {
        'statusCode': 200,
        'body': json.dumps(f'Message {random_number} written to Kinesis successfully!')
    }
