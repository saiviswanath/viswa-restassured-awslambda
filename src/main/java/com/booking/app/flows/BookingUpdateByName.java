package com.booking.app.flows;

import com.booking.app.pojo.BookingByName;
import com.framework.core.api.restclient.RequestParam;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.utils.properties.PropertiesUtils;

public class BookingUpdateByName {
	public static ResponseFetcher bookingUpdateByName(PropertiesUtils properties, String token, String id, String firstname, String lastname){
        RequestParam request;
        request = new RequestParam(properties.getProperty("baseUri"),properties.getProperty("usersBasePath"));
        
        BookingByName name = new BookingByName(firstname, lastname);

        request.setRequestHeaders("Content-Type","application/json");
        request.setRequestHeaders("Accept","application/json");
        request.setRequestHeaders("Cookie","token=" + token);
        
        request.setPathParams("id", id);
        request.setRequestBody(name);
        return request.createRequest().patch("{id}");
    }
}
