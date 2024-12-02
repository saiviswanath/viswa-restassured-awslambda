package com.booking.app.flows;

import com.booking.app.pojo.Booking;
import com.booking.app.pojo.BookingDates;
import com.framework.core.api.restclient.RequestParam;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.utils.properties.PropertiesUtils;

public class BookingCreate {
	public static ResponseFetcher createBooking(PropertiesUtils properties, String firstname, String lastname,
			long totalprice, boolean depositpaid, String checkin, String checkout, String addionalneeds){
        RequestParam request;
        request = new RequestParam(properties.getProperty("baseUri"),properties.getProperty("usersBasePath"));
        
        BookingDates dates = new BookingDates(checkin, checkout);
        Booking booking = new Booking(firstname, lastname, totalprice, depositpaid, dates, addionalneeds);

        request.setRequestHeaders("Content-Type","application/json");
        request.setRequestHeaders("Accept","application/json");
        request.setRequestBody(booking);
        return request.createRequest().post();
    }
	
	public static ResponseFetcher createBookingWithMissingContentType(PropertiesUtils properties, String firstname, String lastname,
			long totalprice, boolean depositpaid, String checkin, String checkout, String addionalneeds){
        RequestParam request;
        request = new RequestParam(properties.getProperty("baseUri"),properties.getProperty("usersBasePath"));
        
        BookingDates dates = new BookingDates(checkin, checkout);
        Booking booking = new Booking(firstname, lastname, totalprice, depositpaid, dates, addionalneeds);

        request.setRequestBody(booking);
        return request.createRequest().post();
    }
}
