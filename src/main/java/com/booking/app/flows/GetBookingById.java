package com.booking.app.flows;

import com.framework.core.api.restclient.RequestParam;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.utils.properties.PropertiesUtils;

public class GetBookingById {
	public static ResponseFetcher getBookings(PropertiesUtils properties, String ids){
        RequestParam request;
        request = new RequestParam(properties.getProperty("baseUri"),properties.getProperty("usersBasePath"));
        request.setPathParams("id", ids);
        request.setRequestHeaders("Accept", "application/json");
        return request.createRequest().get("{id}");
    }
}
