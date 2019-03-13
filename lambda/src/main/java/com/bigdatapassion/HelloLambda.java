package com.bigdatapassion;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.bigdatapassion.dto.RequestClass;
import com.bigdatapassion.dto.ResponseClass;

public class HelloLambda implements RequestHandler<RequestClass, ResponseClass> {

    public ResponseClass handleRequest(RequestClass request, Context context) {
        String greetingString = String.format("Hello %s, %s.", request.getFirstName(), request.getLastName());
        return new ResponseClass(greetingString);
    }

}