package com.anilcakir.intranet.util;

import java.time.LocalDate;
import java.util.Set;

import com.anilcakir.intranet.domain.Employee;
import com.anilcakir.intranet.domain.EmployeePermissionHistory;
import com.anilcakir.intranet.model.PermissionStatus;

public class PermissionBalanceCalculatorUtil {
	
    public static int getBalance(Employee entity) {
    	int balance = 0;
    	balance = getPositiveBalance(entity.getStartDate());
    	balance -= getNegativeBalance(entity.getEmployeePermissionHistoryList());
        return balance;
    }
    
    
    private static int getNegativeBalance(Set<EmployeePermissionHistory> employeePermissionHistoryList) {
    	int negativeBalance = 0;
    	for (EmployeePermissionHistory hist : employeePermissionHistoryList) {
    	    if(hist.getPermissionStatus()==PermissionStatus.CONFIRMED) {
    	    	negativeBalance += hist.getWeekDayCount();
    	    }
    	}
    	return negativeBalance;
	}

	private static int getPositiveBalance(LocalDate startDate) {
		LocalDate LocalDateNow = LocalDate.now();
		int balancePositive = 0;
    	int yearDiff = LocalDateNow.getYear() - startDate.getYear();
    	if(LocalDateNow.getMonthValue() > startDate.getMonthValue() ) {
    		yearDiff--;
    	}
    	if(yearDiff <= 0 ) balancePositive=5;
    	else if(yearDiff<=5) balancePositive = (yearDiff-1)*15;
    	else if(yearDiff<=10) balancePositive = 60 + (yearDiff-5)*18;
    	else balancePositive = 150 + (yearDiff-10)*24;
        
    	return balancePositive;
    }
}
