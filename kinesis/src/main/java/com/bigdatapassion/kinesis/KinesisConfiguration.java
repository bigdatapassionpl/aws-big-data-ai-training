package com.bigdatapassion.kinesis;

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

}
