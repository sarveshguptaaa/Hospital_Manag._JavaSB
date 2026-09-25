package com.projectHM.hosManage.service;


import com.projectHM.hosManage.Entities.Insurance;
import com.projectHM.hosManage.Entities.Patient;
import com.projectHM.hosManage.Repository.InsuranceRepository;
import com.projectHM.hosManage.Repository.Patient_Repo;
import com.projectHM.hosManage.dto.Insurancedto;
import com.projectHM.hosManage.dto.Patientdto;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private final Patient_Repo patientrepo;

    @Autowired
    private InsuranceRepository insuranceRepository;



//    public PatientService(Patient_Repo patientrepo, InsuranceRepository insuranceRepository) {
//        this.patientrepo = patientrepo;
//        this.insuranceRepository = insuranceRepository; // 👈 Ab yeh null nahi rahega
//    }

    public PatientService(Patient_Repo patientrepo) {
        this.patientrepo = patientrepo;
    }

    @Transactional
    public Patient getPatientById(Long id) {
        Patient p1 = patientrepo.findById(id).orElseThrow();
        Patient p2 = patientrepo.findById(id).orElseThrow();
        p1.setName("YOYO");
        return p1;
    }


    //RESULT:-
//    Hibernate:
//    select
//    p1_0.id,
//    p1_0.birthdate,
//    p1_0.email,
//    p1_0.gender,
//    p1_0.name
//            from
//    patient p1_0
//    where
//    p1_0.id=?
//    Hibernate:
//    update
//            patient
//    set
//    birthdate=?,
//    email=?,
//    gender=?,
//    name=?
//    where
//    id=?
//    Patient{id=1, name='YOYO', birthdate=2003-02-01, email='addi26@gmail.com', gender='Male'}
    // DATABSE YHI ADDI26@GMAIL.COM K NAM PHLE aditya gupta tha pr yaha woh chnge hogya aaur hogya abb YOYO in both tbhi hibernate ne updat equery bhi run kri


    @Transactional
    public Optional<Patient> findByInsuranceId(Long insurance_id) {

        Optional<Patient> pats = patientrepo.findByInsuranceId(insurance_id);

        if (pats.isEmpty()) {
            return Optional.empty(); // 👈 Agar khali hai toh empty Optional bhejo
        } else {
            return pats; // 👈 Agar data hai toh wahi return kar do
        }
    }


    @Transactional
    public Optional<Patient> findById(long id) {

        Optional<Patient> pt = patientrepo.findById(id);

        if (pt.isEmpty()) {
            return Optional.empty();
        } else {
            return pt;
        }
    }

    public List<Patient> findAll() {
        return patientrepo.findAll();
    }


    @Transactional
    public List<Patient> deleteAndFetchAll(Long id) {
        //  Pehle database se sach me delete karo
        patientrepo.deleteById(id);

        //  Fir bacha hua saara fresh data nikal kar controller ko de do
        return patientrepo.findAll();
    }


    // PatientService.java
    @Transactional
    public Patient createPatByName(String name, LocalDate birthdate, Long insuranceId) {
        Optional<Patient> existingPatient = patientrepo.findByNameAndBirthdate(name, birthdate);

        if (existingPatient.isEmpty()) {
            Patient newPatient = new Patient();
            newPatient.setName(name);
            newPatient.setBirthdate(birthdate);
            newPatient.setEmail(name.toLowerCase().replaceAll("\\s+", "") + birthdate.getYear() + "@hospital.com");

            // Insurance optional hai - agar aaya toh set karo, nahi toh skip
            if (insuranceId != null) {
                insuranceRepository.findById(insuranceId).ifPresent(newPatient::setInsurance);
            }

            return patientrepo.save(newPatient); // Sirf Patient save hua!
        }
        return null;
    }


    @Transactional
    public Patientdto createPatByEmail(String email, String name, LocalDate birthdate, Long insuranceId, String gender , String bloodgroup) {

        Optional<Patient> ptd = patientrepo.findByEmail(email);

        if (ptd.isEmpty()) {
            Patient newpat = new Patient();
            newpat.setEmail(email);
            newpat.setName(name);
            newpat.setBirthdate(birthdate);
            newpat.setGender(gender);
            newpat.setBloodGroup(bloodgroup);

            if (insuranceId != null) {
                insuranceRepository.findById(insuranceId).ifPresent(newpat::setInsurance);
            }


            Patient savedPatient = patientrepo.save(newpat);

            //  Ab is saved entity ko DTO me convert karke return karo
            Patientdto dto = new Patientdto();
            BeanUtils.copyProperties(savedPatient, dto);

            // Agar insurance hai toh uski DTO bhi bana do
            if (savedPatient.getInsurance() != null) {
                Insurancedto insDto = new Insurancedto();
                BeanUtils.copyProperties(savedPatient.getInsurance(), insDto);
                dto.setInsurance(insDto);
            }

            return dto; // Entity ki jagah DTO return kiya
        }

        return null;
    }


    @Transactional
    public Optional<Patientdto> updateEmail(Long id, String email) {

        Optional<Patient> patientOpt = patientrepo.findById(id);

        if (patientOpt.isEmpty()) {
            return Optional.empty(); // Agar patient nahi mila toh khali haath wapas
        }


        Patient patient = patientOpt.get();
        patient.setEmail(email);
        Patient updatedPatient = patientrepo.save(patient);

        //  Ek jhatke me saari fields (name, gender, bloodGroup, birthdate, etc.) DTO me copy karo
        Patientdto dto = new Patientdto();
        BeanUtils.copyProperties(updatedPatient, dto);

        //  Agar patient ka insurance hai, toh use bhi convert karke set karo
        if (updatedPatient.getInsurance() != null) {
            Insurancedto insDto = new Insurancedto();
            BeanUtils.copyProperties(updatedPatient.getInsurance(), insDto);
            dto.setInsurance(insDto);
        }

        // Appointments automatically copy ho jayengi kyunki naam aur data type same hain

        return Optional.of(dto); // DTO ko Optional me wrap karke bhejo
    }


    @Transactional
    public Optional<Patientdto> updateInsurance(Long id, Long insuranceId) {

        //  Pehle database se purana patient nikaaloo iD ke sahare
        Optional<Patient> pats = patientrepo.findById(id);

        if (pats.isEmpty()) {
            return Optional.empty(); // Agar patient hi nahi mila toh khali haath wapas
        }

        Patient existingPatient = pats.get(); // 👈 Purana patient mil gaya

        //  Ab naya Insurance dhoondo dbs se jo update karna hai
        if (insuranceId != null) {
            Optional<Insurance> newInsurance = insuranceRepository.findById(insuranceId);
            if (newInsurance.isPresent()) {
                // Patient ke andar naya insurance set kar diya
                existingPatient.setInsurance(newInsurance.get());
            } else {
                // Agar insurance id galat hai, toh tum chaho toh exception throw kar sakte ho ya skip
                throw new RuntimeException("Insurance plan not found!");
            }
        } else {
            // Agar front-end se insuranceId null aayi hai, iska matlab patient insurance hatana chahta hai
            existingPatient.setInsurance(null);
        }


        Patient updatedPatient = patientrepo.save(existingPatient);

        //  Ek jhatke me saari fields DTO me copy karo
        Patientdto dto = new Patientdto();
        BeanUtils.copyProperties(updatedPatient, dto);

        // Naye Insurance ki details bhi DTO me convert karke daal do
        if (updatedPatient.getInsurance() != null) {
            Insurancedto insDto = new Insurancedto();
            BeanUtils.copyProperties(updatedPatient.getInsurance(), insDto);
            dto.setInsurance(insDto);
        }

        return Optional.of(dto);
    }

    @Transactional
    public List<Patientdto> deleteAndFetch(Long id) {

        patientrepo.deleteById(id);

        //  Fresh list uthao aur ek line me DTO me convert karke bhej do
        return patientrepo.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()); // 👈 Java 8/11/17 sabme chalega
    }


    private Patientdto convertToDto(Patient patient) {
        Patientdto dto = new Patientdto();
        BeanUtils.copyProperties(patient, dto);

        if (patient.getInsurance() != null) {
            Insurancedto insDto = new Insurancedto();
            BeanUtils.copyProperties(patient.getInsurance(), insDto);
            dto.setInsurance(insDto);
        }
        return dto;
    }


    @Transactional
    public boolean updatePatientByEmail(String email, String gender, String bloodgroup, Long insuranceId) {

        Optional<Patient> patientOptional = patientrepo.findByEmail(email);

        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();

            //  Sirf vahi fields update karein jo pass hue hain
            if (gender != null) {
                patient.setGender(gender);
            }
            if (bloodgroup != null) {
                patient.setBloodGroup(bloodgroup);
            }
            if (insuranceId != null) {
                // patient.setInsurance(...) ya direct insuranceId set karein
            }


            patientrepo.save(patient);
            return true;
        }

        return false; // Record nahi mila
    }

}

