package com.anilcakir.intranet.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.anilcakir.intranet.model.PermissionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class EmployeePermissionHistory {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column
    private LocalDateTime creationDateTime;

    @Column(nullable = false, updatable = true)
    @Enumerated(EnumType.STRING)
    private PermissionStatus permissionStatus;

    @Column    
    private LocalDateTime statusChangeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @CreatedDate
    @Column(nullable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;
    
    @Column
    private int weekDayCount;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public PermissionStatus getPermissionStatus() {
		return permissionStatus;
	}

	public void setPermissionStatus(PermissionStatus permissionStatus) {
		this.permissionStatus = permissionStatus;
	}

	public LocalDateTime getStatusChangeDate() {
		return statusChangeDate;
	}

	public void setStatusChangeDate(LocalDateTime statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public OffsetDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(OffsetDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public OffsetDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(OffsetDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public int getWeekDayCount() {
		return weekDayCount;
	}

	public void setWeekDayCount(int weekDayCount) {
		this.weekDayCount = weekDayCount;
	}

}
