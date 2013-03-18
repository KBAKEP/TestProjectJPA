/**
 * Created on Oct 17, 2011
 */
package ru.artezio.volkov.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.artezio.volkov.entity.*;
import ru.artezio.volkov.service.EmployeeService;

@Service("jpaEmployeeService")
@Repository
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	private Log log = LogFactory.getLog(EmployeeServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	public Employee hire(String name, Department department) {
		log.info("hiring employee " +name + " to department " + department.getName());
		Employee emp = new Employee(name);
		department.addEmployee(emp);
		em.persist(emp);
		return emp;
	};

	public Employee changeDepartment(Employee emp, Department dep) {
		log.info("moving employee " +emp.getName() + " to department " + dep.getName());
		emp.setDepartment(dep);
		em.merge(emp);
		return emp;
	};

	public Employee fire(Employee emp) {
		log.info("firing employee " + emp.getName());
		Department dep = emp.getDepartment();
		dep.getEmployees().remove(emp);
		em.remove(emp);

		return emp;
	};

	public List<Employee> findAll() {
		log.info("getting employee list");
		String queryString = "SELECT e FROM Employee e ";
		Query query = em.createQuery(queryString, Employee.class);

		return query.getResultList();
	};

}
