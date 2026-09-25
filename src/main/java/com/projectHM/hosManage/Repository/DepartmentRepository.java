package com.projectHM.hosManage.Repository;

import com.projectHM.hosManage.Entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}