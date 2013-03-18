package ru.artezio.volkov.service;

import java.util.List;

import ru.artezio.volkov.entity.*;


public interface EmployeeService {

		
	
	public Employee hire(String name, Department department);
	
	public Employee changeDepartment(Employee emp, Department dep);
	
	public Employee fire(Employee emp);
	
	public List<Employee> findAll();
	
}
