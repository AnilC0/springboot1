package com.anilcakir.intranet.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class EmployeeDTO {

    @NotNull
    @Size(max = 64)
    private String firstName;

    @NotNull
    @Size(max = 64)
    private String lastName;

    @NotNull
    private LocalDate startDate;
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

}
