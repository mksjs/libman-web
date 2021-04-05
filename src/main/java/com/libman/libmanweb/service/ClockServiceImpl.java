package com.libman.libmanweb.service;

import java.text.ParseException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libman.libmanweb.entity.Clock;

/**
 * @author manish
 *
 */
@Service
public class ClockServiceImpl implements ClockService {

	@Autowired
	Clock clock;

	@Override
	public Calendar getCalendar() {
		// TODO Auto-generated method stub
		return clock.getCalendar();

	}

	@Override
	public void displayCurrentTime() {
		// TODO Auto-generated method stub
		System.out.println("The set time for LibMan is: " + clock.getCalendar().getTime());
	}

	@Override
	public void resetCalendar() {
		// TODO Auto-generated method stub
		clock.resetCalendar();
	}

	@Override
	public void setCalendar(String newdatestr) throws ParseException {
		// TODO Auto-generated method stub
		clock.setCalendar(newdatestr);

	}

}
