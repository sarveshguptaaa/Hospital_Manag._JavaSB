package com.projectHM.hosManage.Repository;

import com.projectHM.hosManage.Entities.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    Optional<Insurance> findByPolicyNumber(String policyNumber);

    Optional<Insurance> findById(Long id);

//    Insurance save(Insurance insures);




}