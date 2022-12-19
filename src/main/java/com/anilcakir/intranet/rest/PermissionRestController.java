package com.anilcakir.intranet.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anilcakir.intranet.model.EmployeePermissionHistoryDTO;
import com.anilcakir.intranet.model.PermissionStatus;
import com.anilcakir.intranet.service.PermissionService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/permission", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionRestController {

    private final PermissionService permissionService;

    public PermissionRestController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<List<EmployeePermissionHistoryDTO>> getPermissionHistory(@PathVariable Long employeeId) {
    	List<EmployeePermissionHistoryDTO> permissionHistory = permissionService.findAll(employeeId);
    	if(permissionHistory==null || permissionHistory.isEmpty()) {
    		ResponseEntity.notFound().build();
    	}
    	return ResponseEntity.ok(permissionHistory);
    }

    @PostMapping
    public ResponseEntity<Long> createEmployeePermission(@RequestBody @Valid EmployeePermissionHistoryDTO employeePermissionHistoryDTO) {
    	employeePermissionHistoryDTO.setPermissionStatus(PermissionStatus.PENDING);
    	Long response = permissionService.create(employeePermissionHistoryDTO);
    	if(response < 0) {
    		//TODO: responseMessage with respect to errorCode
    		return ResponseEntity.badRequest().build();
    	}
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
