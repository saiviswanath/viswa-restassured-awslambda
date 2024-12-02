package com.booking.app.flows;

import com.booking.app.pojo.BookingResponse;
import com.framework.core.asserts.Asserts;

public class BookingCreateValidation {
	public static void validateCreateBookingResponseBodyFields(BookingResponse resp){
		Asserts.assertTrue(resp.getBooking().getFirstname() != null,"Validate response body has firstname field");
		Asserts.assertTrue(resp.getBooking().getLastname() != null,"Validate response body has lastname field");
		Asserts.assertTrue(resp.getBooking().getTotalprice() != null,"Validate response body has totalprice field");
		Asserts.assertTrue(resp.getBooking().getDepositpaid() != null,"Validate response body has depositpaid field");
		Asserts.assertTrue(resp.getBooking().getBookingdates().getCheckin() != null,"Validate response body has checkin field");
		Asserts.assertTrue(resp.getBooking().getBookingdates().getCheckout() != null,"Validate response body has checkout field");
		Asserts.assertTrue(resp.getBooking().getAdditionalneeds() != null,"Validate response body has additionalNeeds field");
   }
}
