package com.booking.tests;

import java.util.HashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.booking.app.flows.Authenticate;
import com.booking.app.flows.BookingCreate;
import com.booking.app.flows.BookingCreateValidation;
import com.booking.app.flows.BookingUpdateByName;
import com.booking.app.flows.BookingUpdateByNameValidation;
import com.booking.app.pojo.Auth;
import com.booking.app.pojo.AuthResponse;
import com.booking.app.pojo.Booking;
import com.booking.app.pojo.BookingResponse;
import com.framework.core.api.restclient.ResponseBodyParse;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.asserts.Asserts;
import com.framework.core.report.TestNGListener;
import com.framework.core.utils.properties.FrameworkProperties;
import com.framework.core.utils.testdata.ReadTestData;

@Listeners(TestNGListener.class)
public class BookingUpdateByNameTest extends BaseTest {
	   @DataProvider(name = "parupdate_booking_test_data")
	    public Object[][] parUpdateBookingDataProvider() {

	        String testDirName = FrameworkProperties.getFrameworkProperties().getProperty("testDir");
	        String testDataDirName = FrameworkProperties.getFrameworkProperties().getProperty("testDataDirName");
	        String parUpdateBookingTestDataFileName = properties.getProperty("parUpatBookingTestData");
	        String parUpdateBookingTestDataFilePath = testDirName + testDataDirName + appName + "/" + parUpdateBookingTestDataFileName;

	        return ReadTestData.readTestData(parUpdateBookingTestDataFilePath);
	    }
	   
	   @Test(groups = {"smoke", "regression"},dataProvider = "parupdate_booking_test_data")
	    public void parUpdateByNameBookingTest(HashMap<String, String> testData) {
		   	String bookingid = testData.get("bookingid");
	        String firstname = testData.get("firstname");
	        String lastname = testData.get("lastname");
	        
	        ResponseFetcher authResponse = Authenticate.authForBooking(properties);
	        ResponseBodyParse<?> authResponseBodyParse = authResponse.getResponseBodyParse(AuthResponse.class);
	        AuthResponse authResp = (AuthResponse) authResponseBodyParse.getResponse();
	        
	        ResponseFetcher parUpateByNameBookingResponse = BookingUpdateByName.bookingUpdateByName(properties, authResp.getToken(), bookingid, firstname, lastname);
	        //Validate status code
	        Asserts.assertEquals(parUpateByNameBookingResponse.getStatusCode(),200,"Validate status code");

	        ResponseBodyParse<?> responseBodyParse = parUpateByNameBookingResponse.getResponseBodyParse(Booking.class);

	        //Validate user fields
	        BookingUpdateByNameValidation.validateBookingUpdateByNameResponseBodyFields((Booking)responseBodyParse.getResponse());	        
	    }
}
