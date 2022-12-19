package com.anilcakir.intranet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anilcakir.intranet.domain.Employee;
import com.anilcakir.intranet.model.EmployeeDTO;
import com.anilcakir.intranet.model.EmployeeGetDTO;
import com.anilcakir.intranet.repos.EmployeeRepository;
import com.anilcakir.intranet.util.PermissionBalanceCalculatorUtil;


@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeGetDTO> findAll() {
        List<Employee> employeeList = employeeRepository.findAll(Sort.by("id"));
        List<EmployeeGetDTO> employeeResponseList = new ArrayList<EmployeeGetDTO>();
        for (Employee emp : employeeList) {
        	employeeResponseList.add(mapToDTO(emp));
        }
        return employeeResponseList;
    }

    public EmployeeGetDTO get(Long id) {
    	Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent())
        	return null;
    	return mapToDTO(employee.get());
    }

    public Long create(EmployeeDTO employeeDTO) {
        Employee employee = mapToEntity(employeeDTO);
        Long userId = employeeRepository.save(employee).getId();
        return userId;
    }

    public void update(Long id, EmployeeDTO employeeDTO) {
    	Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent())
        	return;
        mapToEntity(employeeDTO);
        employeeRepository.save(employee.get());
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
    
    private EmployeeGetDTO mapToDTO(Employee entity) {
    	EmployeeGetDTO dto = new EmployeeGetDTO();
 		dto.setId(entity.getId());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setStartDate(entity.getStartDate());
		dto.setPermissionBalance(PermissionBalanceCalculatorUtil.getBalance(entity));
        return dto;
    }
    
    private Employee mapToEntity(EmployeeDTO dto) {
    	Employee entity =new Employee();    	
    	entity.setFirstName(dto.getFirstName());
    	entity.setLastName(dto.getLastName());
    	entity.setStartDate(dto.getStartDate());
        return entity;
    }
    

}
