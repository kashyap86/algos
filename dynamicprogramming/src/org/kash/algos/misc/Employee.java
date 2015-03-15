package org.kash.algos.misc;

public class Employee {

	private String firstName;
	private String lastName;
	
	public Employee(String fName, String lName) {
		this.setFirstName(fName);
		this.setLastName(lName);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
