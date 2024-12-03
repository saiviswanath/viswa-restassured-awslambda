package com.booking.tests;

import java.util.HashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.booking.app.flows.Authenticate;
import com.booking.app.flows.BookingUpdate;
import com.booking.app.flows.BookingUpdateValidation;
import com.booking.app.pojo.AuthResponse;
import com.booking.app.pojo.Booking;
import com.framework.core.api.restclient.ResponseBodyParse;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.asserts.Asserts;
import com.framework.core.utils.properties.FrameworkProperties;
import com.framework.core.utils.testdata.ReadTestData;

public class BookingUpdateTest extends BaseTest {
	   @DataProvider(name = "update_booking_test_data")
	    public Object[][] updateBookingDataProvider() {

	        String testDirName = FrameworkProperties.getFrameworkProperties().getProperty("testDir");
	        String testDataDirName = FrameworkProperties.getFrameworkProperties().getProperty("testDataDirName");
	        String updateBookingTestDataFileName = properties.getProperty("updateBookingTestData");
	        String updateBookingTestDataFilePath = testDirName + testDataDirName + appName + "/" + updateBookingTestDataFileName;

	        return ReadTestData.readTestData(updateBookingTestDataFilePath);
	    }
	   
	   @Test(groups = {"smoke", "regression"},dataProvider = "update_booking_test_data")
	    public void updateBookingTest(HashMap<String, String> testData) {
		   	String bookingid = testData.get("bookingid");
		   	String firstname = testData.get("firstname");
	        String lastname = testData.get("lastname");
	        long totalprice = Long.parseLong(testData.get("totalprice"));
	        boolean depositpaid = Boolean.parseBoolean(testData.get("depositpaid"));
	        String additionalneeds = testData.get("additionalneeds");
	        String bookingdatescheckin = testData.get("bookingdatescheckin");
	        String bookingdatescheckout = testData.get("bookingdatescheckout");
	        
	        ResponseFetcher authResponse = Authenticate.authForBooking(properties);
	        ResponseBodyParse<?> authResponseBodyParse = authResponse.getResponseBodyParse(AuthResponse.class);
	        AuthResponse authResp = (AuthResponse) authResponseBodyParse.getResponse();
	        
	        ResponseFetcher updateBookingResponse = BookingUpdate.updateBooking(properties, authResp.getToken(), bookingid, firstname, lastname,
	        		totalprice, depositpaid, bookingdatescheckin, bookingdatescheckout, additionalneeds);
	        //Validate status code
	        Asserts.assertEquals(updateBookingResponse.getStatusCode(),200,"Validate status code");

	        ResponseBodyParse<?> responseBodyParse = updateBookingResponse.getResponseBodyParse(Booking.class);

	        //Validate user fields
	        BookingUpdateValidation.validateBookingUpdateResponseBodyFields((Booking)responseBodyParse.getResponse());	        
	    }
}
