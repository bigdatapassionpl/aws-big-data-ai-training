
~~~shell
aws iam list-roles --query 'Roles[*].[RoleName,Arn]' --output table

aws kinesis create-stream --stream-name kinesis-data-stream-example --shard-count 1

aws sns create-topic --name "PolitechnikaSNSTopic"

aws lambda list-functions --query 'Functions[*].[FunctionName,Runtime,LastModified]' --output table
~~~