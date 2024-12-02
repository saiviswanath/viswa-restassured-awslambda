package com.framework.core.api.restclient;

import io.restassured.response.Response;

public class ResponseBodyParse<T> {
	private T result;
    public ResponseBodyParse(Response response, Class<T> clazz){
    	result = response.as(clazz);    
    }
    
    public T getResponse() {
    	return result;
    }
}
