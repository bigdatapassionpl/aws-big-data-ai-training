#!/usr/bin/env bash

aws lambda delete-function --function-name lambda-function-in-nodejs-simple
aws lambda delete-function --function-name lambda-function-in-nodejs-s3
aws lambda delete-function --function-name lambda-function-in-nodejs-sns-put
