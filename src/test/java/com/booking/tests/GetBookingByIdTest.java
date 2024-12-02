package com.booking.tests;

import java.util.HashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.booking.app.flows.GetBookingById;
import com.booking.app.flows.GetBookingByIdValidation;
import com.booking.app.pojo.Booking;
import com.framework.core.api.restclient.ResponseBodyParse;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.asserts.Asserts;
import com.framework.core.report.TestNGListener;
import com.framework.core.utils.properties.FrameworkProperties;
import com.framework.core.utils.testdata.ReadTestData;

@Listeners(TestNGListener.class)
public class GetBookingByIdTest extends BaseTest {
	   @DataProvider(name = "get_bookingbyid_test_data")
	    public Object[][] getBookingByIdDataProvider() {

	        String testDirName = FrameworkProperties.getFrameworkProperties().getProperty("testDir");
	        String testDataDirName = FrameworkProperties.getFrameworkProperties().getProperty("testDataDirName");
	        String getBookingByidTestDataFileName = properties.getProperty("getBookingByIdTestData");
	        String getBookingByidTestDataFilePath = testDirName + testDataDirName + appName + "/" + getBookingByidTestDataFileName;

	        return ReadTestData.readTestData(getBookingByidTestDataFilePath);
	    }
	   
	@Test(groups = {"smoke", "regression"},dataProvider = "get_bookingbyid_test_data")
	    public void getBookingByIdTest(HashMap<String, String> testData) {
	        String bookingid = testData.get("bookingid");
	        ResponseFetcher getBookingByIdResponse = GetBookingById.getBookings(properties, bookingid);
	        
	        //Validate status code
	        Asserts.assertEquals(getBookingByIdResponse.getStatusCode(),200,"Validate status code");

	        @SuppressWarnings("unchecked")
			ResponseBodyParse<?> responseBodyParse = getBookingByIdResponse.getResponseBodyParse(Booking.class);

	        //Validate user fields
	        GetBookingByIdValidation.validateGetBookingByIdResponseBodyFields((Booking)responseBodyParse.getResponse());
	        	        
	    }
}
