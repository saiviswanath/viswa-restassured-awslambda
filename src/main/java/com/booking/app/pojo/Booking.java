package com.booking.app.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
	private String firstname;
	private String lastname;
	private Long totalprice;
	private Boolean depositpaid;
	private BookingDates bookingdates;
	private String additionalneeds;
}
