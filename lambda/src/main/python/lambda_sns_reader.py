import json

def lambda_handler(event, context):
    sns_message = event['Records'][0]['Sns']['Message']
    # sns_message = json.loads(event['Records'][0]['Sns']['Message'])

    process_sns_message(sns_message)

    return {
        'statusCode': 200,
        'body': json.dumps('SNS message processed successfully!')
    }

def process_sns_message(sns_message):
    print(f"Processing SNS message: {sns_message}")
