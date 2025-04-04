#!/usr/bin/env bash

aws kinesis create-stream --stream-name kinesis-data-stream-example --shard-count 1
