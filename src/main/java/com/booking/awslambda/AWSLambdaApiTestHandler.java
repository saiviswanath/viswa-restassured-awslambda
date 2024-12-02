package com.booking.awslambda;

import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.collections.Lists;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.LambdaException;

public class AWSLambdaApiTestHandler implements RequestHandler<Map<String,String>, String> {
    private static final LambdaClient lambdaClient = LambdaClient.builder().build();
    @Override
    public String handleRequest(Map<String,String> event, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Handler invoked");
        
        TestNG testng = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add("booking-testng.xml");//path to xml..
        testng.setTestSuites(suites);
        testng.run();
        
        //TODO: Reports to AWS S3 post suite run using s3 sdk

        String response = null;
        try {
            response = lambdaClient.serviceName();
        } catch(LambdaException e) {
            logger.log(e.getMessage());
        }
        return response;
    }
}
