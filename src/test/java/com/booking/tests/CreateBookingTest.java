package com.booking.tests;

import java.util.HashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.booking.app.flows.BookingCreate;
import com.booking.app.flows.BookingCreateValidation;
import com.booking.app.pojo.BookingResponse;
import com.framework.core.api.restclient.ResponseBodyParse;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.asserts.Asserts;
import com.framework.core.report.TestNGListener;
import com.framework.core.utils.properties.FrameworkProperties;
import com.framework.core.utils.testdata.ReadTestData;

@Listeners(TestNGListener.class)
public class CreateBookingTest extends BaseTest {
	   @DataProvider(name = "create_booking_test_data")
	    public Object[][] createBookingDataProvider() {

	        String testDirName = FrameworkProperties.getFrameworkProperties().getProperty("testDir");
	        String testDataDirName = FrameworkProperties.getFrameworkProperties().getProperty("testDataDirName");
	        String createBookingTestDataFileName = properties.getProperty("createBookingTestData");
	        String createBookingTestDataFilePath = testDirName + testDataDirName + appName + "/" + createBookingTestDataFileName;

	        return ReadTestData.readTestData(createBookingTestDataFilePath);
	    }
	   
	   @Test(groups = {"smoke", "regression"},dataProvider = "create_booking_test_data")
	    public void createBookingTest(HashMap<String, String> testData) {
	        String firstname = testData.get("firstname");
	        String lastname = testData.get("lastname");
	        long totalprice = Long.parseLong(testData.get("totalprice"));
	        boolean depositpaid = Boolean.parseBoolean(testData.get("depositpaid"));
	        String additionalneeds = testData.get("additionalneeds");
	        String bookingdatescheckin = testData.get("bookingdatescheckin");
	        String bookingdatescheckout = testData.get("bookingdatescheckout");
	        
	        ResponseFetcher createBookingResponse = BookingCreate.createBooking(properties, firstname, lastname, totalprice, depositpaid, bookingdatescheckin, bookingdatescheckout, additionalneeds);
	        //Validate status code
	        Asserts.assertEquals(createBookingResponse.getStatusCode(),200,"Validate status code");

	        ResponseBodyParse<?> responseBodyParse = createBookingResponse.getResponseBodyParse(BookingResponse.class);

	        //Validate user fields
	        BookingCreateValidation.validateCreateBookingResponseBodyFields((BookingResponse)responseBodyParse.getResponse());
	        
	    }
	   
	   @Test(groups = {"regression"},dataProvider = "create_booking_test_data")
	    public void createBookingWithMissingContentTypeTest(HashMap<String, String> testData) {
	        String firstname = testData.get("firstname");
	        String lastname = testData.get("lastname");
	        long totalprice = Long.parseLong(testData.get("totalprice"));
	        boolean depositpaid = Boolean.parseBoolean(testData.get("depositpaid"));
	        String additionalneeds = testData.get("additionalneeds");
	        String bookingdatescheckin = testData.get("bookingdatescheckin");
	        String bookingdatescheckout = testData.get("bookingdatescheckout");
	        
	        ResponseFetcher createBookingResponse = BookingCreate.createBookingWithMissingContentType(properties, firstname, lastname, totalprice, depositpaid, bookingdatescheckin, bookingdatescheckout, additionalneeds);
	        //Validate status code
	        Asserts.assertEquals(createBookingResponse.getStatusCode(),500,"Validate status code");
	        
	    }
}
