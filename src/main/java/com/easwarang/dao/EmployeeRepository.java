package com.easwarang.dao;

import com.easwarang.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

	@Query(value="select sum(employee_salary) from employees where department_id=?1",nativeQuery=true)
	Double costByDept(Long deptId);
	
	@Query(value="select sum(employee_salary) from employees where reporting_id=?1",nativeQuery=true)
	Double costByManager(Long managerId);

}
