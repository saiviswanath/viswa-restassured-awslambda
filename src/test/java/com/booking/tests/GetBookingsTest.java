package com.booking.tests;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.booking.app.flows.GetBookings;
import com.booking.app.flows.GetBookingsValidation;
import com.booking.app.pojo.BookingId;
import com.framework.core.api.restclient.ResponseBodyParse;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.asserts.Asserts;
import com.framework.core.report.TestNGListener;
import com.framework.core.utils.properties.FrameworkProperties;
import com.framework.core.utils.testdata.ReadTestData;

@Listeners(TestNGListener.class)
public class GetBookingsTest extends BaseTest {
	   @DataProvider(name = "get_bookings_test_data")
	    public Object[][] getBookingsDataProvider() {

	        String testDirName = FrameworkProperties.getFrameworkProperties().getProperty("testDir");
	        String testDataDirName = FrameworkProperties.getFrameworkProperties().getProperty("testDataDirName");
	        String getBookingsTestDataFileName = properties.getProperty("getBookingsTestData");
	        String getBookingsTestDataFilePath = testDirName + testDataDirName + appName + "/" + getBookingsTestDataFileName;

	        return ReadTestData.readTestData(getBookingsTestDataFilePath);
	    }
	   
	   @SuppressWarnings("unchecked")
	@Test(groups = {"smoke", "regression"},dataProvider = "get_bookings_test_data")
	    public void getBookingsTest(HashMap<String, String> testData) {
	        String firstname = testData.get("firstname");
	        String lastname = testData.get("lastname");
	        
	        ResponseFetcher getBookingsResponse = GetBookings.getBookings(properties, firstname, lastname);
	        //Validate status code
	        Asserts.assertEquals(getBookingsResponse.getStatusCode(),200,"Validate status code");

	        @SuppressWarnings("unchecked")
			ResponseBodyParse<?> responseBodyParse = getBookingsResponse.getResponseBodyParse((Class<List<BookingId>>)(Object)List.class);

	        //Validate user fields
	        GetBookingsValidation.validateGetBookingsResponseBodyFields(((List<LinkedHashMap<String, Integer>>)responseBodyParse.getResponse()));
	        	        
	    }
}
