package com.bigdatapassion;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamLambda implements RequestStreamHandler {

    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        int letter;
        while ((letter = input.read()) != -1) {
            output.write(Character.toUpperCase(letter));
        }
    }

}