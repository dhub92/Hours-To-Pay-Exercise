package com.exercise.main;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.exercise.model.Employee;
import com.exercise.utils.CalculationMethods;


public class MethodsTests {
		
	public static Employee employee;
	public static String[] informationFormated;
	public static String[] workedDays;
	public static String[] workedHours;
	public static Map<String,String> workedHoursAndDays;
	public static String employeeName;
	public static int totalPayment;
	public static List<String> hoursWorkedByEmployees;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			hoursWorkedByEmployees = CalculationMethods.readFile(); 		
			for(String hours:hoursWorkedByEmployees) {
				informationFormated = CalculationMethods.formatInformation(hours);
				employeeName = CalculationMethods.extractEmployeeName(informationFormated);
				workedDays = CalculationMethods.extractWorkedDay(informationFormated);
				workedHours = CalculationMethods.extractWorkingHours(informationFormated);
				workedHoursAndDays = CalculationMethods.createMapForWorkedHoursAndDays(workedHours, workedDays);
				totalPayment = CalculationMethods.getTotalPayment(workedHoursAndDays);
				
				employee = new Employee(employeeName, workedHoursAndDays, totalPayment);
				System.out.println("The amount to pay "+employee.getEmployeeName()+" is: "+employee.getPayment());
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
		
	

}
