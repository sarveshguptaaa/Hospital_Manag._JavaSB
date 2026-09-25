package com.projectHM.hosManage.Repository;

import com.projectHM.hosManage.Entities.BloodGroupCountResponseEntity;
import com.projectHM.hosManage.Entities.Patient;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
// JUnit 5 (Jupiter) hona chahiye
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface Patient_Repo extends JpaRepository<Patient, Long> {

    // Custom JPQL Query using explicit list mapping
    @Query("SELECT p FROM Patient p WHERE p.name = :name")
    List<Patient> findByName(@Param("name") String name);

    // If birthdate isn't unique, consider changing this to List<Patient>
    // to prevent IncorrectResultSizeDataAccessException at runtime
    Patient findByBirthdate(LocalDate birthdate);

    List<Patient> findByBirthdateOrEmail(LocalDate birthDate, String email);

     List<Patient> findByBirthdateBetween(LocalDate startDate , LocalDate endDate);

     List<Patient> findByNameContainingIgnoreCase(String query);


     //the baove is JPA QUEY METHOD .

      // THE BELWO IS @QUERY THATG LETIS WRITE DIFFERNT ANN DISTINCT , COMPLEX QUIERIES

     //@Query("Select p from Patient p where p.bloodGroup = ?1")
     @Query("Select p from Patient p where p.bloodGroup = :bloodgroup") //kuikiparam mae owh bloodgroup laga h
     List<Patient> findByBloodGroup(@Param("bloodgroup") String bloodGroup);


     @Query("Select p from Patient p where p.birthdate > :birthdate")
     List<Patient> findByBornAfterDate(@Param("birthdate") LocalDate birthdate);


     @Query("Select p.bloodGroup, Count(p) from Patient p group by  p.bloodGroup ")
     List<Object[]> countEachBloodGroupTypessss();



    //NOW NATIVE PURE SQL QUERIES


    @Query(
            value = "SELECT * FROM patient_tab",
            countQuery = "SELECT count(*) FROM patient_tab",
            nativeQuery = true
    )
    Page<Patient> findAllPatients(Pageable pageable);

    //create , insert queries example in nativequery



    @Modifying
    @Transactional
    @Query(value ="Update patient_tab  SET patient_name = :name where id = :id"  , nativeQuery = true)
    int updateNameWithId(@Param("name") String name , @Param("id") Long id);



    //makking columns as a entity to make sure only that data is viible to or provided to user so make it entity

    @Query("Select new com.projectHM.hosManage.Entities.BloodGroupCountResponseEntity(p.bloodGroup ,Count(p)) from Patient p group by  p.bloodGroup ")
    List<BloodGroupCountResponseEntity> countEachBloodGroupType();


    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments")
    List<Patient> findAllPatientWithAppointment();


    @Query(value = "SELECT * FROM patient_tab WHERE insurance_id = :insuranceId", nativeQuery = true)
    Optional<Patient> findByInsuranceId(@Param("insuranceId") Long insuranceId);


//   @Query("Select p from patient_tab p where p.name = :name AND p.birthdate =:birthdate")
//    Optional<Patient>findByNameAndBirthdate(@Param("name") String name , @Param("birthdate") LocalDate birthdate);

    // If entity field is: private LocalDate birthDate;
    Optional<Patient> findByNameAndBirthdate(String name, LocalDate birthdate);

    Optional<Patient> findByEmail(String Email);


    void deleteById(Long id);
}