package com.booking.app.flows;

import com.booking.app.pojo.Booking;
import com.booking.app.pojo.BookingResponse;
import com.framework.core.asserts.Asserts;

public class GetBookingByIdValidation {
	public static void validateGetBookingByIdResponseBodyFields(Booking resp){
		Asserts.assertTrue(resp.getFirstname() != null,"Validate response body has firstname field");
		Asserts.assertTrue(resp.getLastname() != null,"Validate response body has lastname field");
		Asserts.assertTrue(resp.getTotalprice() != null,"Validate response body has totalprice field");
		Asserts.assertTrue(resp.getDepositpaid() != null,"Validate response body has depositpaid field");
		Asserts.assertTrue(resp.getBookingdates().getCheckin() != null,"Validate response body has checkin field");
		Asserts.assertTrue(resp.getBookingdates().getCheckout() != null,"Validate response body has checkout field");
		Asserts.assertTrue(resp.getAdditionalneeds() != null,"Validate response body has additionalNeeds field");
   }
}
