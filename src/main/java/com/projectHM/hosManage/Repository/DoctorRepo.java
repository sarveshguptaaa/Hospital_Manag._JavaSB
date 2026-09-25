package com.projectHM.hosManage.Repository;

import com.projectHM.hosManage.Entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findById(Long id);



    boolean existsByEmail(String email);
}
