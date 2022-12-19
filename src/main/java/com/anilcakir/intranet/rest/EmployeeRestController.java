package com.anilcakir.intranet.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anilcakir.intranet.model.EmployeeDTO;
import com.anilcakir.intranet.model.EmployeeGetDTO;
import com.anilcakir.intranet.service.EmployeeService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/employees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeRestController {

    private final EmployeeService employeeService;

    public EmployeeRestController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeGetDTO>> listEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeGetDTO> getEmployee(@PathVariable final Long id) {
    	EmployeeGetDTO employee = employeeService.get(id);
    	if(employee==null) {
    		return ResponseEntity.notFound().build();
    	}
    	return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<Long> createEmployee(@RequestBody @Valid final EmployeeDTO employeeDTO) {
    	Long employeeId = employeeService.create(employeeDTO); 
    	return new ResponseEntity<>(employeeId, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable final Long id, @RequestBody @Valid final EmployeeDTO employeeDTO) {
        employeeService.update(id, employeeDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable final Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
