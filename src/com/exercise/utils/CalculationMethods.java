package com.exercise.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CalculationMethods {
	private static final String TXT_FILE_PATH = "./src/com/exercise/resource/Hours.txt";
	private static final String[] WORK_DAYS = { "MO", "TU", "WE", "TH", "FR", "SA", "SU" };
	private static final String[] SCHEDULE = { "00:01-09:00", "09:01-18:00", "18:01-00:00" };
	private static final LocalTime FORMAT_24H = LocalTime.of(00, 00);
	private static final int[] HOUR_COST = { 25, 15, 20 };
	private static final int WEEKEND_EXTRA_HOUR_COST = 5;
	private static final int MINUTES_IN_HOUR = 60;
	public static double totalPayment;

	/**
	 * Reads the file stored in TXT_FILE_PATH path
	 * 
	 * @return scheduleWorked The schedule worked by each employee.
	 * @throws FileNotFoundException
	 *             if the file cannot be found it
	 */
	public List<String> readFile() throws FileNotFoundException {

		File hoursFile = new File(TXT_FILE_PATH);
		Scanner scanner = new Scanner(hoursFile);

		List<String> scheduleWorked = new ArrayList<>();
		int i = 0;

		while (scanner.hasNext()) {
			scheduleWorked.add(scanner.next());
			System.out.println(scheduleWorked.get(i));
			i++;

		}
		scanner.close();
		return scheduleWorked;
	}

	/**
	 * Formats the input information from a String object
	 * 
	 * @param inputInformation
	 *            the input information that must follow this structure:
	 *            <EmployeeName>=<DayWorked>:<schedule worked>,<DayWorked>:<schedule
	 *            worked>,.... ASTRID=MO10:00-12:00,TH12:00-14:00,SU20:00-21:00
	 * @return informationFormated the information formated as shows in this
	 *         example: ASTRID MO10:00-12:00 TH12:00-14:00 SU20:00-21:00
	 * @throws NullPointerException
	 *             if inputInformation is null
	 */
	public String[] formatInformation(String inputInformation) throws NullPointerException {
		String[] informationFormated = inputInformation.split("=|,");
		return informationFormated;
	}

	/**
	 * Retrieves the employee's name from an input previously formated by
	 * formatInformation method
	 * 
	 * @param informationFormated
	 *            the information that must be previously formated
	 * @return employeeName the employee's name
	 * @throws NullPointerException
	 *             if informationFormated is null
	 */
	public String extractEmployeeName(String[] informationFormated) throws NullPointerException {
		String employeeName = informationFormated[0];
		return employeeName;
	}

	/**
	 * Retrieves the employee's worked day
	 * 
	 * @param informationFormated
	 *            the information that must be previously formated by
	 *            formatInformation method
	 * @return workDay the day in which the employee worked
	 * @throws NullPointerException
	 *             if informationFormated is null
	 */
	public String[] extractWorkedDay(String[] informationFormated) throws NullPointerException {
		String[] workedDay = new String[informationFormated.length - 1];
		for (int i = 1; i < informationFormated.length; i++)
			workedDay[i - 1] = informationFormated[i].substring(0, 2);
		return workedDay;
	}

	/**
	 * Retrieves the employee's worked hours
	 * 
	 * @param informationFormated
	 *            the information that must be previously formated by
	 *            formatInformation method
	 * @return woredHours the hours worked by the employee
	 * @throws NullPointerException
	 *             if informationFormated is null
	 */
	public String[] extractWorkedHours(String[] informationFormated) throws NullPointerException {
		String[] workedHours = new String[informationFormated.length - 1];
		for (int i = 1; i < informationFormated.length; i++)
			workedHours[i - 1] = informationFormated[i].substring(2);
		return workedHours;
	}

	/**
	 * Creates a Map with the worked days and hours by the employee
	 * 
	 * @param workedHours
	 *            the employee's worked hours
	 * @param workedDays
	 *            the employee's worked days
	 * @return workedHoursAndDaysMap the map that contains the worked days with the
	 *         worked hours by the employee
	 * @throws NullPointerException
	 *             if workedHours and workedDays are null
	 */
	public Map<String, String> createMapForWorkedHoursAndDays(String[] workedHours, String[] workedDays)
			throws NullPointerException {
		Map<String, String> workedHoursAndDaysMap = new HashMap<String, String>();
		if (workedHours.length == workedHours.length) {
			for (int i = 0; i < workedHours.length; i++) {
				workedHoursAndDaysMap.put(workedDays[i], workedHours[i]);
			}
		}
		return workedHoursAndDaysMap;
	}

	/**
	 * Retrieves the total payment of the hours worked in the week to the employee
	 * 
	 * @param workedHoursAndDays
	 *            the worked hours and days linked it in a Map
	 * @return totalPay the employee's total payment
	 * @throws NullPointerException
	 *             if workedHoursAndDays is null
	 * @throws DateTimeParseException
	 *             if the hoursWorked cannot be transformed
	 */
	public double getTotalPayment(Map<String, String> workedHoursAndDays)
			throws NullPointerException, DateTimeParseException {
		totalPayment = 0.0;
		for (String workday : WORK_DAYS) {
			String hoursWorked = workedHoursAndDays.get(workday);
			if (hoursWorked != null) {
				LocalTime[] hoursWorkedTransformed = transformToLocalTimeFormat(hoursWorked);
				if (workday.equals("SA") || workday.equals("SU"))
					totalPayment += calculateDayPayment(hoursWorkedTransformed[0], hoursWorkedTransformed[1], true);
				else
					totalPayment += calculateDayPayment(hoursWorkedTransformed[0], hoursWorkedTransformed[1], false);
			}
		}
		return totalPayment;
	}

	/**
	 * Transforms the hours schedule from String object to LocalTime object
	 * 
	 * @param interval
	 *            the hours schedule. Example: 11:00-12:00
	 * @return intervalHoursInLocalTime the hours schedule transformed in LocalTime
	 *         objects
	 * @throws NullPointerException
	 *             if the interval is null
	 * @throws DateTimeParseException
	 *             if interval cannot be parsed to LocalTime
	 */
	public LocalTime[] transformToLocalTimeFormat(String interval) throws NullPointerException, DateTimeParseException {
		String[] intervalHours = interval.split("-");
		LocalTime[] intervalHoursInLocalTime = new LocalTime[2];

		for (int i = 0; i < intervalHours.length; i++)
			intervalHoursInLocalTime[i] = LocalTime.parse(intervalHours[i]);

		return intervalHoursInLocalTime;

	}

	/**
	 * Retrieves the difference between the starting hour and ending hour of
	 * employee's work schedule
	 * 
	 * @param startingHour
	 *            the starting hour of employee's work schedule
	 * @param endingHour
	 *            the ending hour of employee's work schedule
	 * @return timeDifference the time difference in hours
	 * @throws NullPointerException
	 *             if if startingHour and endingHour are null
	 * @throws DateTimeParseException
	 *             if startingHour and endingHour are cannot be parsed
	 */
	public double differenceBetweenHours(LocalTime startingHour, LocalTime endingHour)
			throws NullPointerException, DateTimeParseException {
		Duration timeDifference = Duration.between(startingHour, endingHour);
		return (double) (timeDifference.toMinutes()) / MINUTES_IN_HOUR;
	}

	/**
	 * Calculates the payment of hours worked on each day of the week to the
	 * employee
	 * 
	 * @param startingHour
	 *            the starting hour of employee's work schedule
	 * @param endingHour
	 *            the ending hour of employee's work schedule
	 * @param isWeekend
	 *            true if the employee worked on saturday or sunday and false if the
	 *            employee worked on monday to friday
	 * @return totalDayPay the employee's pay received on the day worked
	 * @throws NullPointerException
	 *             if startingHour, endingHour and isWeekend are null
	 * @throws DateTimeParseException
	 *             if the interval hours cannot be transformed to LocalTime
	 */
	public double calculateDayPayment(LocalTime startingHour, LocalTime endingHour, boolean isWeekend)
			throws NullPointerException, DateTimeParseException {
		double totalDayPay = 0;
		double firstPay = 0;
		double secondPay = 0;
		int paymentByWorkInWeekend = 0;
		boolean lowBoundaryTime = false;
		boolean topBoundaryTime = false;

		// Verify if the hours were worked in weekend
		paymentByWorkInWeekend = isWeekend ? WEEKEND_EXTRA_HOUR_COST : 0;

		for (int i = 0; i < SCHEDULE.length; i++) {
			LocalTime[] hoursIntervalTransformed = transformToLocalTimeFormat(SCHEDULE[i]);
			LocalTime scheduleStarting = hoursIntervalTransformed[0];
			LocalTime scheduleEnding = hoursIntervalTransformed[1];
			// transform 00:00 (24:00) to 23:59
			if (scheduleEnding == FORMAT_24H)
				scheduleEnding = LocalTime.of(23, 59);

			if (scheduleStarting.equals(startingHour))
				lowBoundaryTime = true;

			if (scheduleEnding.equals(endingHour))
				topBoundaryTime = true;

			if (lowBoundaryTime || topBoundaryTime)
				totalDayPay = (HOUR_COST[i] + paymentByWorkInWeekend)
						* differenceBetweenHours(startingHour, endingHour);
			// if the hours worked interval is inside of the schedule interval
			if (scheduleStarting.isBefore(startingHour) && scheduleEnding.isAfter(endingHour)) {
				totalDayPay = (HOUR_COST[i] + paymentByWorkInWeekend)
						* differenceBetweenHours(startingHour, endingHour);
			}

			// if the hours worked interval overlaps two schedule interval
			if (isOverlaping(scheduleStarting, scheduleEnding, startingHour)) {
				if (differenceBetweenHours(scheduleEnding, endingHour) > 0) {

					firstPay = (HOUR_COST[i] + paymentByWorkInWeekend)
							* differenceBetweenHours(startingHour, scheduleEnding);
					secondPay = (HOUR_COST[i + 1] + paymentByWorkInWeekend)
							* differenceBetweenHours(scheduleEnding, endingHour);
					totalDayPay = firstPay + secondPay;
				}

			}

			lowBoundaryTime = false;
			topBoundaryTime = false;

		}
		return totalDayPay;

	}

	/**
	 * Verify if the working if the working hours overlap two schedules
	 * 
	 * @param scheduleStarting
	 *            the beginning time of a schedule
	 * @param scheduleEnding
	 *            the ending time of a schedule
	 * @param startingWorkedHours
	 *            the beginning time in which the employee started working
	 * @return true if the working hours overlap two schedules and false if it
	 *         doesn't
	 * @throws NullPointerException
	 *             if scheduleStarting, scheduleEnding and/or startingWorkedHours
	 * @throws DateTimeParseException
	 *             if scheduleStarting, scheduleEnding and/or startingWorkedHours
	 *             cannot be transformed to LocalTime
	 */
	public boolean isOverlaping(LocalTime scheduleStarting, LocalTime scheduleEnding,
			LocalTime startingWorkedHours) throws NullPointerException, DateTimeParseException {
		return scheduleStarting.isBefore(startingWorkedHours) && scheduleEnding.isAfter(startingWorkedHours);
	}

}
