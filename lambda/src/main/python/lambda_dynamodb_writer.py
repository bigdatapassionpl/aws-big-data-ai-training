import boto3

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('customer')

def lambda_handler(event, context):
    print("Event:", event)
    table.put_item(Item=event)
