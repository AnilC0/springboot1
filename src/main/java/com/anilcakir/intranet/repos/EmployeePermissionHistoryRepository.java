package com.anilcakir.intranet.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anilcakir.intranet.domain.EmployeePermissionHistory;
import com.anilcakir.intranet.model.PermissionStatus;

import jakarta.transaction.Transactional;


public interface EmployeePermissionHistoryRepository extends JpaRepository<EmployeePermissionHistory, Long> {
	
	@Transactional
	@Modifying
	@Query("UPDATE EmployeePermissionHistory SET permissionStatus = :permissionStatus WHERE id = :id")
	void updateStatus(@Param(value = "id") long EmployeePermissionHistoryId , @Param(value = "permissionStatus") PermissionStatus permissionStatus);

	List<EmployeePermissionHistory> findByEmployee(Long employeeId);

}
