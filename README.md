# Big Data &amp; AI on AWS

## Additional Glue Maven dependency !!!

~~~bash
aws s3 cp s3://aws-glue-jes-prod-us-east-1-assets/etl/jars/glue-assembly.jar glue-assembly.jar

mvn install:install-file \
    -Dfile=glue-assembly.jar \
    -DgroupId=com.amazonaws \
    -DartifactId=aws-java-glue-assembly \
    -Dversion=1.0.0 \
    -Dpackaging=jar
~~~