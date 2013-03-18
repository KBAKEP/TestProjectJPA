package ru.artezio.volkov;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ru.artezio.volkov.entity.Department;
import ru.artezio.volkov.entity.Employee;
import ru.artezio.volkov.service.DepartmentService;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderPersistenceTests {
	
	
	@Autowired
	DepartmentService dService;
		
	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void testService() throws Exception {
		assertNotNull(dService);
		Department dep = dService.create("dep1");
		assertNotNull(dep.getId());
		
		Department dep2 = dService.findById(dep.getId());
		assertEquals(dep.getId(), dep2.getId());
		/*
		Order order = new Order();
		order.getItems().add(new Item());
		entityManager.persist(order);
		entityManager.flush();
		assertNotNull(order.getId());
		*/
	}

	
	@Test
	@Transactional
	public void testSaveDepartmentWithEmployee() throws Exception {
		
		Department dep = dService.create("dep1");
		Employee emp1 = new Employee("firstEmp");
		dService.hire(dep, emp1);
		assertNotNull(emp1.getId());
	}
/*
	@Test
	@Transactional
	public void testSaveDepartmentWithEmployees() throws Exception {
		Department dep = new Department();
		dep.addEmployee(new Employee());
		entityManager.persist(dep);
		entityManager.flush();
		assertNotNull(dep.getId());
		
	}
*/	
	
	@Test
	@Transactional
	public void testSaveAndGet2() throws Exception {
	
		Department other = (Department) entityManager.find(Department.class, 1L);
		assertEquals("Dev", other.getName());
		
	}
	
	@Test
	@Transactional
	public void testSaveAndGet3() throws Exception {
	
		Department other = dService.findByName("Dev");
		
		assertEquals(1L, (long) other.getId());
		
		Department dep = dService.create("third");
		assertEquals(dep.getId(), dService.findByName("third").getId());
	}
	
	@Test
	@Transactional
	public void testSaveAndGet() throws Exception {
		Department dep = new Department();
		Employee emp1 = new Employee("firstEmp");
		dService.hire(dep, emp1);
		entityManager.persist(dep);
		entityManager.flush();
		assertNotNull(dep.getId());
		// Otherwise the query returns the existing order (and we didn't set the
		// parent in the item)...
		entityManager.clear();
		
		Department other = (Department) entityManager.find(Department.class, dep.getId());
		assertEquals(1, other.getEmployees().size());
		assertEquals(other, other.getEmployees().iterator().next().getDepartment());
	}

	@Test
	@Transactional
	public void testSaveAndFind() throws Exception {
		
		Department dep = dService.create("4th");
		Employee e1 = new Employee("1");
		Employee e2 = new Employee("2");
		Employee e3 = new Employee("3");
		dService.hire(dep, e1);
		dService.hire(dep, e2);
		dService.hire(dep, e3);
		entityManager.persist(dep);
		entityManager.flush();
		// Otherwise the query returns the existing order (and we didn't set the
		// parent in the item)...
		entityManager.clear();
		
		List<Employee> empList = dService.findByName("4th").getEmployees();
				
		assertEquals(3, empList.size());
	}

}
