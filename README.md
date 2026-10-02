# Big Data &amp; AI on AWS

## Additional Glue Maven dependency !!!

Currently:

https://docs.aws.amazon.com/glue/latest/dg/aws-glue-programming-etl-libraries.html
https://docs.aws.amazon.com/en_en/glue/latest/dg/aws-glue-programming-etl-libraries.html#develop-local-scala

before:
~~~bash
aws s3 cp s3://aws-glue-jes-prod-us-east-1-assets/etl/jars/glue-assembly.jar glue-assembly.jar

mvn install:install-file \
    -Dfile=glue-assembly.jar \
    -DgroupId=com.amazonaws \
    -DartifactId=aws-java-glue-assembly \
    -Dversion=1.0.0 \
    -Dpackaging=jar
~~~

~~~bash
java17
mvn clean install -P uber
aws s3 cp kinesis/target/kinesis-1.0-SNAPSHOT-jar-with-dependencies.jar s3://radek-datasets-public/ --profile radek
~~~

~~~shell
aws kinesis create-stream --stream-name kinesis-data-stream-example --shard-count 3
aws kinesis list-streams
aws kinesis delete-stream --stream-name kinesis-data-stream-example
~~~

~~~bash
aws s3 cp s3://radek-datasets-public/kinesis-1.0-SNAPSHOT-jar-with-dependencies.jar .

java -cp kinesis-1.0-SNAPSHOT-jar-with-dependencies.jar com.bigdatapassion.kinesis.KinesisProducerExample -n 1000 -b 1 -t 500 -s kinesis-data-stream-example
~~~
