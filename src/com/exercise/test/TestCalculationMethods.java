package com.exercise.test;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.exercise.utils.CalculationMethods;

public class TestCalculationMethods {
	private CalculationMethods cm;

	@Before
	public void setUp() {
		cm = new CalculationMethods();
	}

	@Test
	public void testFormatInformation() {
		String[] expected = { "DAVID", "MO" };
		String[] actual = cm.formatInformation("DAVID=MO");
		assertArrayEquals(expected, actual);
	}

	@Test(expected = NullPointerException.class)
	public void testExceptionFormatInformation() {
		cm.formatInformation(null);
	}

	@Test
	public void testExtractEmployeeName() {
		String[] information = { "DAVID", "este no debe salir" };
		String expected = "DAVID";
		String actual = cm.extractEmployeeName(information);
		assertEquals(expected, actual);
	}

	@Test(expected = NullPointerException.class)
	public void testExceptionExtractEmployeeName() {
		cm.extractEmployeeName(null);
	}

	@Test
	public void testExtractWorkedDay() {
		String[] information = { "este no debe salir", "MO" };
		String[] expected = { "MO" };
		String[] actual = cm.extractWorkedDay(information);
		assertArrayEquals(expected, actual);
	}

	@Test(expected = NullPointerException.class)
	public void testExceptionExtractWorkedDay() {
		cm.extractWorkedDay(null);

	}

	@Test
	public void testExtractWorkedHours() {
		String[] information = { "este no debe salir", "MO12:00-13:00" };
		String[] expected = { "12:00-13:00" };
		String[] actual = cm.extractWorkedHours(information);
		assertArrayEquals(expected, actual);
	}

	@Test(expected = NullPointerException.class)
	public void testExceptionExtractWorkedHours() {
		cm.extractWorkedHours(null);
	}

	@Test
	public void testCreateMapForWorkedHoursAndDays() {
		String[] workedHours = { "12:00-13:00", "12:00-14:00" };
		String[] workedDays = { "MO", "TU" };
		Map<String, String> expected = new HashMap<String, String>();
		expected.put("MO", "12:00-13:00");
		expected.put("TU", "12:00-14:00");
		Map<String, String> actual = cm.createMapForWorkedHoursAndDays(workedHours, workedDays);
		assertEquals(expected, actual);
	}

	@Test(expected = NullPointerException.class)
	public void testExceptionCreateMapForWorkedHoursAndDays() {
		String[] workedHours = { "12:00-13:00", "12:00-14:00" };
		String[] workedDays = { "MO", "TU" };
		cm.createMapForWorkedHoursAndDays(workedHours, null);
		cm.createMapForWorkedHoursAndDays(null, workedDays);
		cm.createMapForWorkedHoursAndDays(null, null);
	}

	@Test
	public void testTransformToLocalTimeFormat() {
		String interval = "15:00-18:00";
		LocalTime[] expected = { LocalTime.parse("15:00"), LocalTime.parse("18:00") };
		LocalTime[] actual = cm.transformToLocalTimeFormat(interval);
		assertEquals(expected, actual);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullExceptionTransformToLocalTimeFormat() {
		cm.transformToLocalTimeFormat(null);
	}
	
	@Test(expected = DateTimeParseException.class)
	public void testDateTimeParseExceptionTransformToLocalTimeFormat() {
		cm.transformToLocalTimeFormat("");
		cm.transformToLocalTimeFormat("asd");
		cm.transformToLocalTimeFormat(" ");
				
	}

	@Test
	public void testCalculateDayPayment() {
		LocalTime startingHour = LocalTime.parse("12:00");
		LocalTime endingHour = LocalTime.parse("16:00");
		double expected = 60.0;
		double expectedInWekeend = 80.0;
		double actual = cm.calculateDayPayment(startingHour, endingHour, false);
		double actualInWekeend = cm.calculateDayPayment(startingHour, endingHour, true);
		assertEquals(expected, actual, 0.01);
		assertEquals(expectedInWekeend, actualInWekeend, 0.01);
	}
	
	@Test(expected = NullPointerException.class)
	public void testNullExceptionCalculateDayPayment() {
		LocalTime startingHour = LocalTime.parse("12:00");
		LocalTime endingHour = LocalTime.parse("16:00");
		
		cm.calculateDayPayment(null, endingHour, false);
		cm.calculateDayPayment(startingHour, null, true);
		cm.calculateDayPayment(startingHour, endingHour, false);
		cm.calculateDayPayment(null, endingHour, true);
		
	}

	@Test(expected = DateTimeParseException.class)
	public void testDateTimeParseExceptionCalculateDayPayment() {
		LocalTime startingHour = LocalTime.parse("");
		LocalTime endingHour = LocalTime.parse("");
		
		cm.calculateDayPayment(startingHour, endingHour, false);
		cm.calculateDayPayment(startingHour, endingHour, true);
		
	}
	
	@Test
	public void testDifferenceBetwaeenHours() {
		double actual = cm.differenceBetwaeenHours(LocalTime.parse("10:00"), LocalTime.parse("11:00"));
		assertEquals(1.0, actual, 0.01);
	}

	@Test(expected=NullPointerException.class)
	public void testNullExceptionDifferenceBetwaeenHours() {
		cm.differenceBetwaeenHours(null, null);
	}
}
