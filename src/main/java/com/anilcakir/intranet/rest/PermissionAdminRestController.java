package com.anilcakir.intranet.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anilcakir.intranet.model.PermissionStatus;
import com.anilcakir.intranet.model.PermissionUpdateStatusDTO;
import com.anilcakir.intranet.service.PermissionService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/admin/permission", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionAdminRestController {

    private final PermissionService permissionService;

    public PermissionAdminRestController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    public ResponseEntity<Long> updatePermissionStatus(@RequestBody @Valid PermissionUpdateStatusDTO permissionUpdateStatusDTO) {
    	if(permissionUpdateStatusDTO.getPermissionStatus()==PermissionStatus.PENDING)
    		return ResponseEntity.badRequest().build();
    	permissionService.updatePermissionStatus(permissionUpdateStatusDTO);
    	return ResponseEntity.ok().build();
    }
}
