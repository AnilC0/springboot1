package com.anilcakir.intranet.model;

import jakarta.validation.constraints.NotNull;

public class PermissionUpdateStatusDTO {
	
    private Long id;
    
    @NotNull
    private PermissionStatus permissionStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PermissionStatus getPermissionStatus() {
		return permissionStatus;
	}

	public void setPermissionStatus(PermissionStatus permissionStatus) {
		this.permissionStatus = permissionStatus;
	}
    
    
}
