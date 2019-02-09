package com.exercise.model;

import java.util.Map;

public class Employee {
	private String employeeName;
	private Map<String,String> workedHoursAndDays;
	private int payment;
	
	public Employee(String employeeName, Map<String, String> workedHoursAndDays, int payment) {
		super();
		this.employeeName = employeeName;
		this.workedHoursAndDays = workedHoursAndDays;
		this.payment = payment;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Map<String, String> getWorkedHoursAndDays() {
		return workedHoursAndDays;
	}

	public void setWorkedHoursAndDays(Map<String, String> workedHoursAndDays) {
		this.workedHoursAndDays = workedHoursAndDays;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}
	
	

}
