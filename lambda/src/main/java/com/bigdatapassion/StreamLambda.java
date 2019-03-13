package com.bigdatapassion;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamLambda implements RequestStreamHandler {

    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        int letter;
        try {
            while ((letter = inputStream.read()) != -1) {
                outputStream.write(Character.toUpperCase(letter));
            }
            Thread.sleep(3000); // Intentional delay for testing the getRemainingTimeInMillis() result.
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Print info from the context object
        context.getLogger().log("Function name: " + context.getFunctionName());
        context.getLogger().log("Max mem allocated: " + context.getMemoryLimitInMB());
        context.getLogger().log("Time remaining in milliseconds: " + context.getRemainingTimeInMillis());
        context.getLogger().log("CloudWatch log stream name: " + context.getLogStreamName());
        context.getLogger().log("CloudWatch log group name: " + context.getLogGroupName());
    }

}