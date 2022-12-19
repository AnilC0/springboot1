package com.anilcakir.intranet.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anilcakir.intranet.domain.Employee;
import com.anilcakir.intranet.domain.EmployeePermissionHistory;
import com.anilcakir.intranet.model.EmployeePermissionHistoryDTO;
import com.anilcakir.intranet.model.PermissionUpdateStatusDTO;
import com.anilcakir.intranet.repos.EmployeePermissionHistoryRepository;
import com.anilcakir.intranet.repos.EmployeeRepository;
import com.anilcakir.intranet.util.PermissionBalanceCalculatorUtil;


@Service
public class PermissionService {

    private EmployeePermissionHistoryRepository employeePermissionHistoryRepository;
    private EmployeeRepository employeeRepository;

    public PermissionService(EmployeePermissionHistoryRepository employeePermissionHistoryRepository,
    		EmployeeRepository employeeRepository) {
        this.employeePermissionHistoryRepository = employeePermissionHistoryRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeePermissionHistoryDTO> findAll(Long employeeId) {
        List<EmployeePermissionHistory> employeePermissionHistoryList = new ArrayList<EmployeePermissionHistory>();
        Optional<Employee> response = employeeRepository.findById(employeeId);
        if(!response.isPresent()){
        	return null;
        }
        employeePermissionHistoryList.addAll(response.get().getEmployeePermissionHistoryList());
        List<EmployeePermissionHistoryDTO> employeePermissionHistoryDTOList = new ArrayList<EmployeePermissionHistoryDTO>();
        for(EmployeePermissionHistory employeePermissionHistory: employeePermissionHistoryList) {
        	employeePermissionHistoryDTOList.add(mapToDTO(employeePermissionHistory));
        }
        return employeePermissionHistoryDTOList;
    }

    public Long create(EmployeePermissionHistoryDTO employeePermissionHistoryDTO) {
    	Employee employee = employeeRepository.findById(employeePermissionHistoryDTO.getEmployeeId()).get();
    	if(employeePermissionHistoryDTO.getStartDate().isAfter(employeePermissionHistoryDTO.getEndDate()) 
    			|| employee.getStartDate().isAfter(employeePermissionHistoryDTO.getStartDate()) ) {
    		return -1L;
    	}
    	if(employeePermissionHistoryDTO.getStartDate().getDayOfWeek() == DayOfWeek.SATURDAY 
    			|| employeePermissionHistoryDTO.getStartDate().getDayOfWeek() == DayOfWeek.SUNDAY 
    			|| employeePermissionHistoryDTO.getEndDate().getDayOfWeek() == DayOfWeek.SATURDAY 
    			|| employeePermissionHistoryDTO.getEndDate().getDayOfWeek() == DayOfWeek.SUNDAY ) {
    		return -2L;
    	}
        EmployeePermissionHistory employeePermissionHistory = mapToEntity(employeePermissionHistoryDTO);
        int balance = PermissionBalanceCalculatorUtil.getBalance(employeePermissionHistory.getEmployee());
        if(balance < employeePermissionHistory.getWeekDayCount()) {
        	return -3L;
        }
        Long resultId = employeePermissionHistoryRepository.save(employeePermissionHistory).getId();
        return resultId;
    }

    public void updatePermissionStatus(PermissionUpdateStatusDTO permissionUpdateStatusDTO) {
        employeePermissionHistoryRepository.updateStatus(permissionUpdateStatusDTO.getId() , permissionUpdateStatusDTO.getPermissionStatus());
    }

    private EmployeePermissionHistoryDTO mapToDTO(EmployeePermissionHistory entity) {
    	final EmployeePermissionHistoryDTO dto = new EmployeePermissionHistoryDTO();
    	dto.setId(entity.getId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setCreationDateTime(entity.getCreationDateTime());
        dto.setPermissionStatus(entity.getPermissionStatus());
        dto.setStatusChangeDate(entity.getStatusChangeDate());
        if(entity.getEmployee() != null) {
        	dto.setEmployeeId(entity.getEmployee().getId());        	
        }
        return dto;
    }

    private EmployeePermissionHistory mapToEntity(EmployeePermissionHistoryDTO dto) {
    	EmployeePermissionHistory entity = new EmployeePermissionHistory();
    	entity.setStartDate(dto.getStartDate());
    	entity.setEndDate(dto.getEndDate());
    	int weekDayCount = calculateWeekDays(dto.getStartDate(), dto.getEndDate());
    	entity.setWeekDayCount(weekDayCount);
    	entity.setCreationDateTime(dto.getCreationDateTime());
    	entity.setPermissionStatus(dto.getPermissionStatus());
    	entity.setStatusChangeDate(dto.getStatusChangeDate());
    	if(dto.getEmployeeId() != null) {
    		Employee emp = employeeRepository.findById(dto.getEmployeeId()).get();
    		entity.setEmployee(emp);
    	}
        return entity;
    }

	private int calculateWeekDays(LocalDate startDate, LocalDate endDate) {
		int counter = 1;
		for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)){
			if(date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() == DayOfWeek.SUNDAY ) {
				counter++;
			}
		}
		return counter;
	}

}
