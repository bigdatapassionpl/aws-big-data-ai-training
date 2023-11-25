#!/usr/bin/env bash

aws lambda delete-function --function-name lambda_context
aws lambda delete-function --function-name lambda_dynamodb_writer
aws lambda delete-function --function-name lambda_hello
aws lambda delete-function --function-name lambda_kinesis_reader
aws lambda delete-function --function-name lambda_s3_thumbnail
aws lambda delete-function --function-name lambda_sns_reader
aws lambda delete-function --function-name lambda_sns_writer