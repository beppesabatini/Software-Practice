package gof.ch05_04.iterator;

import java.io.IOException;

import gof.ch05_04.iterator.List;
import gof.ch05_04.iterator.ListIterator.ListDirection;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 265. Part of the sample code used to illustrate the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern. The Demo
 * class below does not appear in the manual, and is included to let users test
 * the sample code. </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Demo {

	private static List<Employee> employeeList = initEmployeeList();

	private static List<Employee> initEmployeeList() {
		List<Employee> employeeList = new EmployeeList();
		employeeList.append(new Employee("Alpha"));
		employeeList.append(new Employee("Bravo"));
		employeeList.append(new Employee("Charlie"));
		employeeList.append(new Employee("Delta"));
		employeeList.append(new Employee("Echo"));
		employeeList.append(new Employee("Foxtrot"));
		employeeList.append(new Employee("Golf"));
		employeeList.append(new Employee("Hotel"));
		employeeList.append(new Employee("India"));
		employeeList.append(new Employee("Juliet"));
		employeeList.append(new Employee("Kilo"));
		employeeList.append(new Employee("Lima"));
		return (employeeList);
	}

	public static void main(String[] args) {
		testIterators();
	}

	static void testIterators() {
		System.out.println(" • Iterating forwards through employees: ");
		ListIterator<Employee> employeesForwards = new ListIterator<Employee>(employeeList, ListDirection.FORWARDS);
		printEmployees(employeesForwards);
		System.out.println();

		System.out.println(" • Iterating backwards through employees: ");
		ListIterator<Employee> employeesBackwards = new ListIterator<Employee>(employeeList, ListDirection.BACKWARDS);
		printEmployees(employeesBackwards);
		System.out.println();

		System.out.println(" • Now iterating forwards with an internal ListTraversal: ");
		ListTraverser<Employee> internalTraversal = new ListTraverser<Employee>(employeeList);
		internalTraversal.traverse();
		System.out.println();

		System.out.println(" • Now iterating with a subclass that counts total employees: ");
		ListTraverser<Employee> printNEmployees = new PrintNEmployees<Employee>(employeeList, 10);
		printNEmployees.traverse();
		System.out.println();
	}

	static void printEmployees(ListIterator<Employee> employeeIterator) {
		for (employeeIterator.first(); employeeIterator.isDone() == false; employeeIterator.next()) {
			Employee currentEmployee = null;
			try {
				currentEmployee = employeeIterator.currentItem();
			} catch (IOException ioException) {
				System.err.println(ioException.getMessage());
			}
			System.out.println(" -- Last Name: " + currentEmployee.getLastName());
		}
	}
}
