import json
import boto3
import random
from datetime import datetime

kinesis_stream_name = 'kinesis-data-stream-example'

NAMES = [
    "John Doe", "Jane Smith", "Alice Johnson", "Bob Brown", "Charlie Green",
    "Emily White", "David Black", "Fiona Blue", "George Miller", "Hannah Davis"
]
COUNTRIES = ["Poland", "United States", "Germany", "United Kingdom", "France", "Canada", "Australia", "Japan"]
CITIES = ["Warsaw", "New York", "Berlin", "London", "Paris", "Toronto", "Sydney", "Tokyo"]
STREETS = ["Marszałkowska", "Broadway", "Kurfürstendamm", "Piccadilly", "Champs-Élysées", "Yonge St", "George St", "Shibuya Crossing"]
ANIMALS = ["Dog", "Cat", "Lion", "Tiger", "Elephant", "Giraffe", "Bear", "Monkey", "Rabbit", "Fox"]

def generate_message(num):
    return {
        'partitionkey': f'partitionkey-{num}',
        'currentdate': datetime.now().strftime("%d-%m-%Y %H:%M:%S"),
        'name': random.choice(NAMES),
        'phonenumber': f"+{random.randint(10, 99)} {random.randint(100, 999)} {random.randint(100, 999)} {random.randint(100, 999)}",
        'country': random.choice(COUNTRIES),
        'city': random.choice(CITIES),
        'streetname': random.choice(STREETS),
        'number': str(random.randint(1, 150)),
        'animal': random.choice(ANIMALS)
    }

def lambda_handler(event, context):

    random_number = random.randint(1, 1000)
    data_to_put = generate_message(random_number)

    kinesis_client = boto3.client('kinesis')

    response = kinesis_client.put_record(
        StreamName=kinesis_stream_name,
        Data=json.dumps(data_to_put),
        PartitionKey=data_to_put['partitionkey']
    )

    print(f"SequenceNumber: {response['SequenceNumber']}")

    return {
        'statusCode': 200,
        'body': json.dumps(f"Message for {data_to_put['name']} written to Kinesis successfully!")
    }

