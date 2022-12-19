package com.anilcakir.intranet.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;


public class EmployeePermissionHistoryDTO {

    private Long id;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private LocalDateTime creationDateTime;

    @NotNull
    private PermissionStatus permissionStatus;

    private LocalDateTime statusChangeDate;

    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(final LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public PermissionStatus getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(final PermissionStatus permissionStatus) {
        this.permissionStatus = permissionStatus;
    }

    public LocalDateTime getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(final LocalDateTime statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
}
