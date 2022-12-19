package com.anilcakir.intranet.model;

public class EmployeeGetDTO extends EmployeeDTO{

    private Long id;
    
    private int permissionBalance;
    
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

	public int getPermissionBalance() {
		return permissionBalance;
	}

	public void setPermissionBalance(int permissionBalance) {
		this.permissionBalance = permissionBalance;
	}

}
