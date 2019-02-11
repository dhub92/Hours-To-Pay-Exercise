package com.exercise.main;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import com.exercise.model.Employee;
import com.exercise.utils.CalculationMethods;

public class MethodsUseExample {

	public static Employee employee;
	public static String[] informationFormated;
	public static String[] workedDays;
	public static String[] workedHours;
	public static Map<String, String> workedHoursAndDays;
	public static String employeeName;
	public static double totalPayment;
	public static List<String> hoursWorkedByEmployees;

	public static void main(String[] args) {
		CalculationMethods cm = new CalculationMethods();
		try {
			hoursWorkedByEmployees = cm.readFile();
			for (String hours : hoursWorkedByEmployees) {
				informationFormated = cm.formatInformation(hours);
				employeeName = cm.extractEmployeeName(informationFormated);
				workedDays = cm.extractWorkedDay(informationFormated);
				workedHours = cm.extractWorkedHours(informationFormated);
				workedHoursAndDays = cm.createMapForWorkedHoursAndDays(workedHours, workedDays);
				totalPayment = cm.getTotalPayment(workedHoursAndDays);

				employee = new Employee(employeeName, workedHoursAndDays, totalPayment);
				System.out.println("The amount to pay " + employee.getEmployeeName() + " is: " + employee.getPayment());
			}

		} catch (DateTimeParseException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			e.getStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
