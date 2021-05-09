package com.bigdatapassion.kinesis;

import org.apache.commons.lang3.ObjectUtils;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;

import java.io.IOException;
import java.util.Properties;

public class KinesisConfiguration {

    private Properties prop = new Properties();

    public void load() {
        try {
            prop.load(KinesisConsumerExample.class.getClassLoader().getResourceAsStream("kinesis.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(prop);
    }

    public String getStreamName() {
        return prop.getProperty("kinesis.stream.name");
    }

    public Region getRegion() {
        return Region.of(prop.getProperty("kinesis.region.name"));
    }

    public AwsCredentialsProvider createCredentialsProvider() {
        String accessKey = prop.getProperty("aws_access_key_id");
        String secretKey = prop.getProperty("aws_secret_access_key");
        String sessionToken = prop.getProperty("aws_session_token");

        if (ObjectUtils.allNotNull(accessKey, secretKey, sessionToken)) {
            AwsCredentials awsCredentials = AwsSessionCredentials.create(accessKey, secretKey, sessionToken);
            return StaticCredentialsProvider.create(awsCredentials);
        } else {
            return DefaultCredentialsProvider.create();
        }
    }

}
