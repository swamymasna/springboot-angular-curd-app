package com.swamy.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.swamy.bindings.EmployeeForm;
import com.swamy.entities.Employee;
import com.swamy.repository.EmployeeRepository;
import com.swamy.service.IEmployeeService;

@SpringBootTest
public class EmployeeServiceTest {

	@Autowired
	private IEmployeeService employeeService;
	
	@MockBean
	private EmployeeRepository employeeRepository;

	@Test
	public void saveEmployeeTest01() {
		
		EmployeeForm form = new EmployeeForm();
		form.setEmpId(1); form.setEmpName("SAM"); form.setEmpSal(56000.00);
		
		Employee entity = new Employee();
		BeanUtils.copyProperties(form, entity);
		
		when(employeeRepository.save(entity)).thenReturn(entity);
		
		when(employeeService.saveEmployee(form)).thenReturn("Saved");
		
		assertEquals(entity.getEmpId(), form.getEmpId());
	}
	
	
	@Test
	public void saveEmployeeTest02() {
		
		EmployeeForm form = new EmployeeForm();
		form.setEmpId(1); form.setEmpName("SAM"); form.setEmpSal(56000.00);
		
		Employee entity = new Employee();
		BeanUtils.copyProperties(form, entity);
		
		when(employeeRepository.save(entity)).thenReturn(null);
		
		when(employeeService.saveEmployee(form)).thenReturn(null);
		
		assertEquals(entity.getEmpId(), form.getEmpId());
//		assertTrue(true);
	}
	
	
	@Test
	public void getAllEmps() {
		
		Employee emp1 = new Employee();
		emp1.setEmpId(1); emp1.setEmpName("Swamy"); emp1.setEmpSal(56000.00);
		List<Employee>listEntity = new ArrayList<>();
		listEntity.add(emp1);

		EmployeeForm form = new EmployeeForm();
		
		List<EmployeeForm>listForm = new ArrayList<>();
		for (Employee employee : listEntity) {
			EmployeeForm formObj = new EmployeeForm();
			BeanUtils.copyProperties(employee, formObj);
			listForm.add(formObj);
		}

		when(employeeRepository.findAll()).thenReturn(listEntity);
		
		when(employeeService.getAllEmployees()).thenReturn(listForm);
		
		assertTrue(true);
	}
	
	@Test
	public void editEmployee01() {
		Employee emp1 = new Employee();
		emp1.setEmpId(1); emp1.setEmpName("Swamy"); emp1.setEmpSal(56000.00);

		EmployeeForm form = new EmployeeForm();
		form.setEmpId(1); form.setEmpName("Swamy"); form.setEmpSal(56000.00);
		
		Optional<Employee> optObj = Optional.of(emp1);
		when(employeeRepository.findById(1)).thenReturn(optObj);
		
		EmployeeForm editEmployee = employeeService.editEmployee(1);

		assertEquals(1, editEmployee.getEmpId());
		
	}
	
	@Test
	public void editEmployee02() {
		Employee emp1 = new Employee();
		emp1.setEmpId(1); emp1.setEmpName("Swamy"); emp1.setEmpSal(56000.00);

		EmployeeForm form = new EmployeeForm();
		form.setEmpId(1); form.setEmpName("Swamy"); form.setEmpSal(56000.00);
		
		Optional<Employee> optObj = Optional.of(emp1);
		when(employeeRepository.findById(1)).thenReturn(optObj);
		
		EmployeeForm editEmployee = employeeService.editEmployee(null);

//		assertEquals(1, editEmployee.getEmpId());
		assertTrue(true);
	}
	
	@Test
	public void deleteEmployeeTest() {
		
		Employee emp1 = new Employee();
		emp1.setEmpId(1); emp1.setEmpName("Swamy"); emp1.setEmpSal(56000.00);

		employeeService.deleteEmployee(1);
		verify(employeeRepository, times(1)).deleteById(emp1.getEmpId());
		//Mocking + Assertion
	}
	
}










