package ru.artezio.volkov.service;

import java.util.List;

import ru.artezio.volkov.entity.Department;
import ru.artezio.volkov.entity.Employee;

public interface DepartmentService {

	public Department create(String name);
	
	public Department findById(Long id);
	
	public List<Department> findAll();

	public List<Department> employeeList(Department dep);
	
	public Department findByName(String name);
	
	public Employee hire(Department dep, Employee emp);
	
}
