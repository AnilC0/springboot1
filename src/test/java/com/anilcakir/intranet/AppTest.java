package com.anilcakir.intranet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.anilcakir.intranet.model.EmployeeDTO;
import com.anilcakir.intranet.model.EmployeeGetDTO;
import com.anilcakir.intranet.model.EmployeePermissionHistoryDTO;
import com.anilcakir.intranet.model.PermissionStatus;
import com.anilcakir.intranet.model.PermissionUpdateStatusDTO;
import com.anilcakir.intranet.rest.EmployeeRestController;
import com.anilcakir.intranet.rest.PermissionAdminRestController;
import com.anilcakir.intranet.rest.PermissionRestController;

public class AppTest extends BaseClassTest{

	@Autowired
    private EmployeeRestController employeeRestController;
	
	@Autowired
    private PermissionRestController permissionRestController;
	
	@Autowired
    private PermissionAdminRestController permissionAdminRestController;
	
	@Autowired
	HomeController homeController;
	
	
    private EmployeeDTO generateEmployeeDTO() {
    	EmployeeDTO req = new EmployeeDTO();
    	req.setFirstName("Anıl");
    	req.setLastName("ÇAKIR");
    	req.setStartDate(LocalDate.now());
    	return req;
    }
    
    private EmployeePermissionHistoryDTO generateValidPermission() {
    	EmployeePermissionHistoryDTO req = new EmployeePermissionHistoryDTO();
    	//dayCount == 6
    	req.setStartDate(LocalDate.of(2023, 1, 13));
    	req.setEndDate(LocalDate.of(2023, 1, 20));
    	return req;
    }
    
    private EmployeePermissionHistoryDTO generateWeekEndPermission() {
    	EmployeePermissionHistoryDTO req = new EmployeePermissionHistoryDTO();
    	//throw error
    	req.setStartDate(LocalDate.of(2023, 1, 15));
    	req.setEndDate(LocalDate.of(2023, 1, 20));
    	return req;
    }

    @Test
    public void listEmployee_HttpStatusTest() throws Exception {
    	ResponseEntity<List<EmployeeGetDTO>> employeeListResponse = employeeRestController.listEmployees();
    	assertTrue(employeeListResponse.getStatusCode().equals(HttpStatus.OK), "::listEmployee_HttpStatusTest : listEmployees HttpStatus Fail");
    }

    @Test
    public void listEmployee_NullTest() throws Exception {
    	ResponseEntity<List<EmployeeGetDTO>> employeeListResponse = employeeRestController.listEmployees();
    	assertNotNull(employeeListResponse.getBody() , "::listEmployee_NullTest : listEmployees list Null");
    }
    
    @Test
    public void listEmployee_EmptyListTest() throws Exception {
    	ResponseEntity<List<EmployeeGetDTO>> employeeListResponse = employeeRestController.listEmployees();
		assertFalse(employeeListResponse.getBody().isEmpty(), "::listEmployee_EmptyListTest : listEmployees list empty");
    }
    

    
    @Test
    public void createGetUpdateDeleteEmployeeFlowTest() throws Exception{
    	EmployeeDTO req = generateEmployeeDTO();
    	ResponseEntity<Long> saveResponse = employeeRestController.createEmployee(req);
    	assertTrue(saveResponse.getStatusCode().equals(HttpStatus.OK), "::createGetUpdateDeleteEmployeeFlowTest : createEmployee HttpStatus Fail");

    	ResponseEntity<EmployeeGetDTO> getResponse = employeeRestController.getEmployee(saveResponse.getBody());
    	assertTrue(getResponse.getStatusCode().equals(HttpStatus.OK), "::createGetUpdateDeleteEmployeeFlowTest : getEmployee HttpStatus Fail");
    	
    	req.setFirstName("SUKRU ANIL");
    	ResponseEntity<Void> updateResponse = employeeRestController.updateEmployee(saveResponse.getBody(), req);
    	assertTrue(updateResponse.getStatusCode().equals(HttpStatus.OK), "::createGetUpdateDeleteEmployeeFlowTest : updateEmployee HttpStatus Fail");
    	
    	
    	ResponseEntity<Void> deleteResponse = employeeRestController.deleteEmployee(saveResponse.getBody());
    	assertTrue(deleteResponse.getStatusCode().equals(HttpStatus.OK), "::createGetUpdateDeleteEmployeeFlowTest : deleteEmployee HttpStatus Fail");
    	
    }

    
    
    
    
//
    @Test
    public void listPermission_HttpStatusTest() throws Exception {
    	ResponseEntity<List<EmployeeGetDTO>> employeeListResponse = employeeRestController.listEmployees();
    	assertTrue(employeeListResponse.getStatusCode().equals(HttpStatus.OK), "::listPermission_HttpStatusTest : listEmployees HttpStatus Fail");
    }

    @Test
    public void listPermission_NullTest() throws Exception {
    	ResponseEntity<List<EmployeeGetDTO>> employeeListResponse = employeeRestController.listEmployees();
    	assertNotNull(employeeListResponse.getBody() , "::listPermission_NullTest : listEmployees list Null");
    }
    
    @Test
    public void listPermission_EmptyListTest() throws Exception {
    	ResponseEntity<List<EmployeeGetDTO>> employeeListResponse = employeeRestController.listEmployees();
		assertFalse(employeeListResponse.getBody().isEmpty(), "::listPermission_EmptyListTest : listEmployees list empty");
    }
    
    @Test
    public void permissionFlowOldEmployee_test() throws Exception{    	
    	EmployeeDTO oldEmployee = generateEmployeeDTO();
    	oldEmployee.setFirstName("OLD");
    	oldEmployee.setStartDate(LocalDate.of(2009, 5, 24));
    	
    	ResponseEntity<Long> saveResponse = employeeRestController.createEmployee(oldEmployee);
    	assertTrue(saveResponse.getStatusCode().equals(HttpStatus.OK), "::permissionFlowOldEmployee_test : createEmployee HttpStatus Fail");

    	ResponseEntity<EmployeeGetDTO> getResponse = employeeRestController.getEmployee(saveResponse.getBody());
    	assertTrue(getResponse.getStatusCode().equals(HttpStatus.OK), "::permissionFlowOldEmployee_test : getEmployee HttpStatus Fail");
    	assertTrue(getResponse.getBody().getPermissionBalance()==198, "::permissionFlowOldEmployee_test : getEmployee Balance Fail");
    	
    	EmployeePermissionHistoryDTO req = generateValidPermission();
    	req.setEmployeeId(saveResponse.getBody());
    	
    	ResponseEntity<Long> permissionSaveResponse = permissionRestController.createEmployeePermission(req);
    	assertTrue(saveResponse.getStatusCode().equals(HttpStatus.OK), "::permissionFlowOldEmployee_test : createEmployeePermission HttpStatus Fail");
    	
    	
    	ResponseEntity<List<EmployeePermissionHistoryDTO>> getPermissionResponse = permissionRestController.getPermissionHistory(saveResponse.getBody());
    	assertTrue(getPermissionResponse.getStatusCode().equals(HttpStatus.OK), "::permissionFlowOldEmployee_test : getEmployee HttpStatus Fail");
    	assertTrue(getPermissionResponse.getBody().size()==1, "::permissionFlowOldEmployee_test : getEmployee Balance Fail");
    	
    	
    	PermissionUpdateStatusDTO updateRequest = new PermissionUpdateStatusDTO();
    	updateRequest.setId(permissionSaveResponse.getBody());
    	updateRequest.setPermissionStatus(PermissionStatus.CONFIRMED);
    	permissionAdminRestController.updatePermissionStatus(updateRequest);
    	assertTrue(saveResponse.getStatusCode().equals(HttpStatus.OK), "::permissionFlowOldEmployee_test : updatePermissionStatus HttpStatus Fail");
    	
    	ResponseEntity<EmployeeGetDTO> getResponseAfter = employeeRestController.getEmployee(saveResponse.getBody());
    	assertTrue(getResponseAfter.getStatusCode().equals(HttpStatus.OK), "::permissionFlowOldEmployee_test : getEmployee getResponseAfterHttpStatus Fail");
    	assertTrue(getResponseAfter.getBody().getPermissionBalance()==192, "::permissionFlowOldEmployee_test : getEmployee getResponseAfter new Balance Fail");
    	
    }
    
    @Test
    public void getPermissionList_UndefinedIdTest() throws Exception{
    	ResponseEntity<List<EmployeePermissionHistoryDTO>> getResponse = permissionRestController.getPermissionHistory(101L);
    	assertFalse(getResponse.getStatusCode().equals(HttpStatus.OK), "::createGetUpdateDeleteEmployeeFlowTest : getPermissionHistory HttpStatus Fail");
    }
    
    @Test
    public void getEmployee_UndefinedIdTest() throws Exception{
    	ResponseEntity<EmployeeGetDTO> getResponse = employeeRestController.getEmployee(101L);
    	assertFalse(getResponse.getStatusCode().equals(HttpStatus.OK), "::getEmployeeUndefinedId_test : getEmployee HttpStatus Fail");
    }
    
//Base Controllers
    @Test
    public void HomeControllerCredit() throws Exception{
    	assertTrue(homeController.credit().equals("S. Anil CAKIR"), "");
    }
    
    @Test
    public void HomeControllerStat() throws Exception{
    	assertTrue(homeController.stat().equals("OK"), "");
    }
    
    @Test
    public void HomeControllerVersion() throws Exception{
    	assertTrue(homeController.version().equals("V1.0"), "");
    }
    
    
    @Test
    public void permissionFlowNewEmployee_test() throws Exception{    	
    	EmployeeDTO oldEmployee = generateEmployeeDTO();
    	oldEmployee.setFirstName("New");
    	oldEmployee.setStartDate(LocalDate.of(2022, 5, 24));
    	
    	ResponseEntity<Long> saveResponse = employeeRestController.createEmployee(oldEmployee);
    	assertTrue(saveResponse.getStatusCode().equals(HttpStatus.OK), "::permissionFlowNewEmployee_test : createEmployee HttpStatus Fail");

    	ResponseEntity<EmployeeGetDTO> getResponse = employeeRestController.getEmployee(saveResponse.getBody());
    	assertTrue(getResponse.getStatusCode().equals(HttpStatus.OK), "::permissionFlowNewEmployee_test : getEmployee HttpStatus Fail");
    	assertTrue(getResponse.getBody().getPermissionBalance()==5, "::permissionFlowNewEmployee_test : getEmployee Balance Fail");
    	
    	EmployeePermissionHistoryDTO req = generateWeekEndPermission();
    	req.setEmployeeId(saveResponse.getBody());
    	
    	//fail
    	ResponseEntity<Long> permissionSaveResponse = permissionRestController.createEmployeePermission(req);
    	assertFalse(permissionSaveResponse.getStatusCode().equals(HttpStatus.OK), "::permissionFlowNewEmployee_test : createEmployeePermission HttpStatus Fail");
    	
    	
    	ResponseEntity<List<EmployeePermissionHistoryDTO>> getPermissionResponse = permissionRestController.getPermissionHistory(saveResponse.getBody());
    	assertTrue(getPermissionResponse.getStatusCode().equals(HttpStatus.OK), "::permissionFlowNewEmployee_test : getEmployee HttpStatus Fail");
    	assertTrue(getPermissionResponse.getBody().size()==0, "::permissionFlowNewEmployee_test : getEmployee Balance Fail");
    	
    }
}
