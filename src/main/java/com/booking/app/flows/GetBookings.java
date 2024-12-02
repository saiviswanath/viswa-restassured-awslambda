package com.booking.app.flows;

import com.framework.core.api.restclient.RequestParam;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.utils.properties.PropertiesUtils;

public class GetBookings {
	public static ResponseFetcher getBookings(PropertiesUtils properties, String firstname, String lastname){
        RequestParam request;
        request = new RequestParam(properties.getProperty("baseUri"),properties.getProperty("usersBasePath"));
        request.setQueryParams("firstname", firstname);
        request.setQueryParams("lastname", lastname);
        return request.createRequest().get();
    }
}
