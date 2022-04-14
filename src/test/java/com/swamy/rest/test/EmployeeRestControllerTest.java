package com.swamy.rest.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swamy.bindings.EmployeeForm;
import com.swamy.rest.EmployeeRestController;
import com.swamy.service.IEmployeeService;

@WebMvcTest(value = EmployeeRestController.class)
public class EmployeeRestControllerTest {

	@MockBean
	private IEmployeeService empService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getAllEmployeesTest() throws Exception {
		
		EmployeeForm emp1 = new EmployeeForm();
		emp1.setEmpId(7878); emp1.setEmpName("SWAMY"); emp1.setEmpSal(56000.00);
		
		List<EmployeeForm>listForm = new ArrayList<>();
		listForm.add(emp1);
		
		when(empService.getAllEmployees()).thenReturn(listForm);
		
		//1.create the Request Builder
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/list");
		
		//2.send the Http Request
		ResultActions perform = mockMvc.perform(requestBuilder);
		
		//3. capture the Response
		MvcResult mvcResult = perform.andReturn();
		
		MockHttpServletResponse response = mvcResult.getResponse();
		int status = response.getStatus();
		
		Integer actualValue = 200;
		
		assertEquals(status, actualValue);
	}
	
	@Test
	public void saveEmployeeTest() throws Exception {
		
		EmployeeForm form = new EmployeeForm();
		form.setEmpId(7878); form.setEmpName("SWAMY"); form.setEmpSal(56000.00);
		when(empService.saveEmployee(form)).thenReturn("Employee Saved");
		
		ObjectMapper mapper = new ObjectMapper();
		String saved = mapper.writeValueAsString(form);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employee/save").contentType("application/json").content(saved);
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		int status = response.getStatus();
		int actualValue = 201;
		
		assertEquals(status, actualValue);
	}
	
	@Test
	public void editEmployeeTest() throws Exception {
		
		EmployeeForm emp1 = new EmployeeForm();
		emp1.setEmpId(1); emp1.setEmpName("SWAMY"); emp1.setEmpSal(56000.00);
		
		when(empService.editEmployee(1)).thenReturn(emp1);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/1");
		
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		int status = response.getStatus();
		int actualValue = 200;
		
		assertEquals(status, actualValue);
		
	}
	
	@Test
	public void deleteEmployeeTest() throws Exception {
		
		EmployeeForm emp1 = new EmployeeForm();
		emp1.setEmpId(1); emp1.setEmpName("SWAMY"); emp1.setEmpSal(56000.00);
		List<EmployeeForm> list = new ArrayList<>();
		list.add(emp1);
		
		when(empService.deleteEmployee(1)).thenReturn(list);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/employee/1");
		ResultActions perform = mockMvc.perform(requestBuilder);
		MvcResult mvcResult = perform.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		int status = response.getStatus();
		int actual = 200;
		
		assertEquals(status, actual);
	}
}








