package com.booking.app.flows;

import java.util.LinkedHashMap;
import java.util.List;

import com.framework.core.asserts.Asserts;

public class GetBookingsValidation {
	public static void validateGetBookingsResponseBodyFields(List<LinkedHashMap<String, Integer>> resp){
        Asserts.assertTrue(resp.size() > 0,"Validate response body array size");
        
        resp.forEach(pojo -> {
        	Asserts.assertTrue(pojo.get("bookingid") != null,"Validate response body has name field");
        });
        
    }
}
