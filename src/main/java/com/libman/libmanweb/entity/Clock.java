package com.libman.libmanweb.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author manish
 *
 */
public class Clock {
	Calendar calendar = new GregorianCalendar();

	public Clock() {
		calendar = Calendar.getInstance();
	}

	public Calendar getCalendar() {
		return calendar;
	}
	
	public void setCalendar(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/dd hh:mm");
        Date date = sdf.parse(dateString);

        calendar.setTime(date);
    }

    public void resetCalendar() {
        calendar = Calendar.getInstance();
    }
}
