package com.projectHM.hosManage.Repository;

import com.projectHM.hosManage.Entities.Department;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartementRepository {

    Optional<Department> findByDname(String dname);
}
