package com.libman.libmanweb.service;

import java.text.ParseException;
import java.util.Calendar;

import org.springframework.stereotype.Component;

/**
 * @author manish
 *
 */
@Component("ClockService")
public interface ClockService {

	Calendar getCalendar();

	void displayCurrentTime();

	void resetCalendar();

	void setCalendar(String newdatestr) throws ParseException;

}
