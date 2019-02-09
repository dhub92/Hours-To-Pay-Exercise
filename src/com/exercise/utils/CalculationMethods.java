package com.exercise.utils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CalculationMethods {
	private static final String TXT_FILE_PATH = "F:\\David\\EclipseWorkspace\\Hours-calculation\\src\\com\\exercise\\resource\\Hours.txt";
	private static final String[] WORK_DAYS = {"MO","TU","WE","TH","FR","SA","SU"};
	private static final String[] HOURS_INTERVAL = {"00:01-09:00","09:01-18:00","18:01-00:00"};
	private static final LocalTime FORMAT_24H = LocalTime.of(00, 00);
	private static final int[] HOUR_COST = {25, 15,20};
	private static final int WEEKEND_EXTRA_HOUR_COST = 5;
	public static int totalPaid;
	
	public static List<String> readFile() throws IOException {
		File file = new File(TXT_FILE_PATH); 
		Scanner scanner = new Scanner(file);
		
		List<String> scheduleWorked = new ArrayList<>();
		int i = 0;
		
		while(scanner.hasNext()) {
			scheduleWorked.add(scanner.next());
			System.out.println(scheduleWorked.get(i));
			i++;
			
		}
		scanner.close();
		return scheduleWorked;
	}
		
	/**
	 * Format the input information
	 * @param input 
	 * @return informationFormated 
	 */
	public static String[] formatInformation(String input) {
		String[] informationFormated = input.split("=|,");
		return informationFormated;
	}
	
	/**
	 * Extract the employee's name from an input previously formated by formatInformation method
	 * @param informationFormated  
	 * @return employeeName
	 */
	public static String extractEmployeeName (String[] informationFormated) {
		String employeeName = "";
		employeeName = informationFormated[0];
		return employeeName;
	}
	
	public static String[] extractWorkedDay(String[] informationFormated) {
		String[] workedDay= new String[informationFormated.length-1];
		for(int i = 1;i<informationFormated.length;i++)
			workedDay[i-1]=informationFormated[i].substring(0, 2);
		return workedDay;
	}
	
	public static String[] extractWorkingHours(String[] informationFormated) {
		String[] workingHours = new String[informationFormated.length-1];
		for(int i = 1;i<informationFormated.length;i++)
			workingHours[i-1]=informationFormated[i].substring(2);
		return workingHours;
	}
	
	public static void printResults(String[] restultsToBePrinted) {
		for(String item:restultsToBePrinted)
			System.out.println(item);
		
	}
	
	public static Map<String,String> createMapForWorkedHoursAndDays(String[] workingHours, String[] workedDays){
		Map<String, String> map = new HashMap<String,String>();
		for(int i = 0;i<workingHours.length;i++) {
			map.put(workedDays[i], workingHours[i]);
		}
		return map;
	}
	
	
	public static int getTotalPayment(Map<String, String> workedHoursAndDays) {
		totalPaid = 0;
		for(String workday:WORK_DAYS) {
			
			String hoursWorked = workedHoursAndDays.get(workday); 
			
			if(hoursWorked!=null) {
				
				LocalTime[] hoursWorkedTransformed = CalculationMethods.transformToLocalTimeFormat(hoursWorked);
				
				if(workday.equals("SA") || workday.equals("SU"))
					totalPaid += calculatePaymentForHours(hoursWorkedTransformed[0], hoursWorkedTransformed[1], true);
				else 
					totalPaid += calculatePaymentForHours(hoursWorkedTransformed[0], hoursWorkedTransformed[1], false);
			}
						
		}
		return totalPaid;
	}
	
	public static LocalTime[] transformToLocalTimeFormat(String interval){
		String[] intervalHours = interval.split("-");
		LocalTime[] intervalHoursInLocalTime = new LocalTime[2];
		
		for(int i = 0 ;i<intervalHours.length;i++)
			intervalHoursInLocalTime[i] = LocalTime.parse(intervalHours[i]); 
				
		return intervalHoursInLocalTime;
	
	}
	
	public static int differenceBetwaeenHours(LocalTime startTime, LocalTime endingTime) {
		Duration timeDifference = Duration.between(startTime, endingTime);
		return (int)(timeDifference.toHours());
	}
	
	public static int calculatePaymentForHours(LocalTime startTime, LocalTime endingTime, boolean isWeekend) {
		int totalToPay = 0;
		int paymentByWorkInWeekend = 0;
		boolean lowBoundaryTime = false;
		boolean topBoundaryTime = false;
		
		//Verify if the hours were worked in weekend
		paymentByWorkInWeekend = isWeekend ? WEEKEND_EXTRA_HOUR_COST : 0;
						
		for(int i = 0;i<HOURS_INTERVAL.length;i++) {
			LocalTime[] hoursIntervalTransformed=transformToLocalTimeFormat(HOURS_INTERVAL[i]);
			
			//transform 00:00 (24:00) to 23:59  
			if(hoursIntervalTransformed[1]==FORMAT_24H)
				hoursIntervalTransformed[1] = LocalTime.of(23, 59);
			
			if(hoursIntervalTransformed[0] == startTime)
				lowBoundaryTime = true;
			
			if(hoursIntervalTransformed[1] == endingTime)
				topBoundaryTime = true;
						
			if(lowBoundaryTime == true || topBoundaryTime == true)
				totalToPay = (HOUR_COST[i]+paymentByWorkInWeekend)*differenceBetwaeenHours(startTime, endingTime);
						
			if(hoursIntervalTransformed[0].isBefore(startTime)&&hoursIntervalTransformed[1].isAfter(endingTime)) 
				totalToPay = (HOUR_COST[i]+paymentByWorkInWeekend)*differenceBetwaeenHours(startTime, endingTime);
			
			lowBoundaryTime = false;
			topBoundaryTime = false;
			
		}
		return totalToPay;
		
	}
	
	

}
