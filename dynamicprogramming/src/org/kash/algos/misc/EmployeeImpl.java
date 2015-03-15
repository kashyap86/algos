package org.kash.algos.misc;

public class EmployeeImpl {

	public static void main(String[] args) {
		Employee emp1 = new Employee("first", "last");
		
		Employee emp2 = modifyEmployee(emp1);
		System.out.println(emp2.getFirstName() + " " + emp2.getLastName());
		System.out.println(emp1.getFirstName() + " " + emp1.getLastName());
	}

	private static Employee modifyEmployee(Employee emp1) {
		emp1.setFirstName("newFirst");
		emp1.setLastName("newLast");
		return emp1;
	}
}
