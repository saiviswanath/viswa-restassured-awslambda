package com.booking.app.flows;

import com.booking.app.pojo.Booking;
import com.booking.app.pojo.BookingDates;
import com.framework.core.api.restclient.RequestParam;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.utils.properties.PropertiesUtils;

public class BookingUpdate {
	public static ResponseFetcher updateBooking(PropertiesUtils properties, String token, String id, String firstname, String lastname,
			long totalprice, boolean depositpaid, String checkin, String checkout, String addionalneeds){
        RequestParam request;
        request = new RequestParam(properties.getProperty("baseUri"),properties.getProperty("usersBasePath"));
        
        BookingDates dates = new BookingDates(checkin, checkout);
        Booking booking = new Booking(firstname, lastname, totalprice, depositpaid, dates, addionalneeds);

        request.setRequestHeaders("Content-Type","application/json");
        request.setRequestHeaders("Accept","application/json");
        request.setRequestHeaders("Cookie","token=" + token);
        
        request.setPathParams("id", id);
        request.setRequestBody(booking);
        return request.createRequest().put("{id}");
    }
}
