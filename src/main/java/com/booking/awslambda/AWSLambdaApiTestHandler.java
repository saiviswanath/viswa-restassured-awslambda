package com.booking.awslambda;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.collections.Lists;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class AWSLambdaApiTestHandler implements RequestHandler<Map<String, String>, String> {
    //private static final LambdaClient lambdaClient = LambdaClient.builder().build();
    @Override
    public String handleRequest(Map<String, String> event, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Handler invoked");
        
        
        String[] arr = event.get("testngfile").split(",");
        
        TestNG testng = new TestNG();
        List<String> suites = Lists.newArrayList();
        
        for (String s : arr) {
        	logger.log("Adding " + s + " to suite execution");
        	String path = System.getProperty("user.dir") + "/" + s;
        	logger.log("Path actual: " + path);
        	suites.add(path);//path to xml..
        }
        
        testng.setTestSuites(suites);
        testng.run();
        
        //TODO: Reports to AWS S3 post suite run using s3 sdk
        S3ClientBuilder s3 = S3Client.builder()
                .region(Region.AP_SOUTH_1);
        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket("restassuredlambda")
                .key("")
                .build();
        // https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/s3/src/main/java/com/example/s3/PutObjectMetadata.java
        
        return "Success";
    }
}
