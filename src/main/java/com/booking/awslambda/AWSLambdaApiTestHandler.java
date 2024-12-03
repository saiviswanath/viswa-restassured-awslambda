package com.booking.awslambda;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.testng.TestNG;
import org.testng.collections.Lists;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.framework.core.utils.helper.MiscUtilities;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class AWSLambdaApiTestHandler implements RequestHandler<Map<String, String>, String> {
    //private static final LambdaClient lambdaClient = LambdaClient.builder().build();
    @Override
    public String handleRequest(Map<String, String> event, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Handler invoked");
        String response = "SUCCESS";
        
        String testngFiles = System.getenv("TESTNG_FILES");
        response = executeTestNGSuiteFIles(testngFiles, logger);
        
        if (response.startsWith("FAIL")) {
        	return response;
        }
 
        String s3Bucket = System.getenv("REPORT_S3_BUCKET");
        response = copyReportsToS3(s3Bucket, logger);
       
        return response;
    }
    
    private String executeTestNGSuiteFIles(String tesstNGFiles, LambdaLogger logger) {
    	if (tesstNGFiles == null) {
    		return "FAIL: No Testng Suite Files to execute";
    	}
       String[] testngFilesArr = tesstNGFiles.split(",");
       if (testngFilesArr.length > 0) {
        
	        TestNG testng = new TestNG();
	        List<String> suites = Lists.newArrayList();
	        
	        for (String s : testngFilesArr) {
	        	String path = System.getProperty("user.dir") + File.separator + "testng" + s;
	        	logger.log("Suite File Path: " + path);
	        	suites.add(path);
	        }
	        
	        testng.setTestSuites(suites);
	        testng.run();
	        return "PASS: Ran Suite Files";
       } else {
    	   return "FAIL: No suite files to execute";
       }
    }
    
    private String copyReportsToS3(String s3Bucket, LambdaLogger logger) {
    	//TODO: Reports to AWS S3 post suite run using s3 sdk
    	String reportDir = "/tmp/reports/extent/" + MiscUtilities.dateFormat("T+0", "MM_dd_yyyy");
    	Path reportPath = getLatestReportFile(reportDir, logger);
    	if (reportPath != null) {
    	
	        String reportFileName = "Run_";
	
	        String s3ObjectKey = MiscUtilities.dateFormat("T+0", "MM_dd_yyyy") + File.separator + reportFileName
					+ MiscUtilities.dateFormat("T+0", "MM_dd_yyyy") + "_"
					+ MiscUtilities.getTimeStamp("local").replace("-", "").replace(":", "");
	        
	        logger.log("S3 Dest Object key: " + s3ObjectKey);
	        
	        try {
	    	
	        S3Client s3 = S3Client.builder()
	                .region(Region.AP_SOUTH_1).build();
	        PutObjectRequest putOb = PutObjectRequest.builder()
	                .bucket(s3Bucket)
	                .key(s3ObjectKey)
	                .build();
	        
	        s3.putObject(putOb, RequestBody.fromFile(new File(reportPath.toString())));
	        } catch (S3Exception e) {
				logger.log("S3 Error occured");
				return "FAIL: S3 Error Occurred";
			}
    	} else {
    		return "FAIL: Report Path is null";
    	}
        return "PASS: S3 Report Copy Success";
    }
    
    private Path getLatestReportFile(String sdir, LambdaLogger logger) {
    	Path dir = Paths.get(sdir);
    	Optional<Path> opPath = Optional.empty();
        if (Files.isDirectory(dir)) {
			try {
				opPath = Files.list(dir)
				  .filter(p -> !Files.isDirectory(p))
				  .sorted((p1, p2)-> Long.valueOf(p2.toFile().lastModified())
				    .compareTo(p1.toFile().lastModified()))
				  .findFirst();
			} catch (IOException e) {
				logger.log("IOException on fetching Report file");
			}

            if (opPath.isPresent()){
                return opPath.get();
            }
        }

        return null;
    }
}
