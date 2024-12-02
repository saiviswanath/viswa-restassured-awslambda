package com.booking.app.flows;

import com.booking.app.pojo.Auth;
import com.framework.core.api.restclient.RequestParam;
import com.framework.core.api.restclient.ResponseFetcher;
import com.framework.core.utils.properties.PropertiesUtils;

public class Authenticate {
	public static ResponseFetcher authForBooking(PropertiesUtils properties){
        RequestParam request;
        request = new RequestParam(properties.getProperty("baseUri"),properties.getProperty("authBasePath"));
        
        Auth auth = new Auth(properties.getProperty("authUname"), properties.getProperty("authPwd"));

        request.setRequestHeaders("Content-Type","application/json");
        request.setRequestBody(auth);
        return request.createRequest().post();
    }
}
