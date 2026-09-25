package com.projectHM.hosManage.Controllers;

import com.projectHM.hosManage.Entities.Patient;
import com.projectHM.hosManage.Repository.Patient_Repo;
import com.projectHM.hosManage.dto.Insurancedto;
import com.projectHM.hosManage.dto.Patientdto;
import com.projectHM.hosManage.service.PatientService;
import jakarta.annotation.Nullable;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class PatientController {

    @Autowired
    private Patient_Repo patientRepository;

    @Autowired
    private PatientService patientService;

    @GetMapping("/patientall")
    public ResponseEntity<List<Patient>> findAll() {

        List<Patient> pat = patientService.findAll();

        if (pat.isEmpty()) { // .isEmpty() is preferred over .size() == 0
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.ok(pat);
        }
    }

    @GetMapping("patient/id/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {

        Optional<Patient> pats = patientService.findById(id);

        if (pats.isPresent()) {
            // If it exists, return it with a 200 OK status
            return ResponseEntity.ok(pats.get());
        } else {

            Map<String, Object> fallbackBody = new HashMap<>();
            fallbackBody.put("id", 10000L);
            fallbackBody.put("name", null);


            return ResponseEntity.status(404).body(fallbackBody);
        }
    }


    @GetMapping("patient/insurance/{insuranceId}")
    public ResponseEntity<Patientdto> findByInsuranceId(@PathVariable Long insuranceId) {


        Optional<Patient> patientOpt = patientService.findByInsuranceId(insuranceId);

        if (patientOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // Agar patient nahi mila toh 404
        }

        Patient patient = patientOpt.get();

        // Entity ko DTO me convert //map karo
        Patientdto dto = new Patientdto();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setEmail(patient.getEmail());
        dto.setBirthdate(patient.getBirthdate());
        dto.setGender(patient.getGender());
        dto.setBloodGroup(patient.getBloodGroup());
        dto.setBloodGroupType(patient.getBloodGroupType());
        dto.setCreatedAt(patient.getCreatedAt());

        // Agar Patient ke paas Insurance hai, toh Insurance ka bhi DTO banao
        if (patient.getInsurance() != null) {
            Insurancedto insDto = new Insurancedto();

             insDto.setPolicyNumber(patient.getInsurance().getPolicyNumber()); // jo bhi tumhari fields ho insurance me

            dto.setInsurance(insDto); // Patientdto me set kar diya
        }

        //  Final Response return karo
        return ResponseEntity.ok(dto);
    }

    @PostMapping("patient/name/{name}")
    public boolean createPatByName(
            @PathVariable String name,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate birthdate,
            @RequestParam(required = false) Long insurance_id) { 


        Patient newPatient = patientService.createPatByName(name, birthdate, insurance_id);

        return newPatient != null && newPatient.getId() != null;
    }


    @PostMapping("patient/email/{email}")
    public boolean createPatByEmail(
            @PathVariable String email,
            @RequestParam String name,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate birthdate,
            @RequestParam(required = false) Long insuranceId,

            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String bloodgroup) {


        Patientdto newPat = patientService.createPatByEmail(email, name, birthdate, insuranceId, gender , bloodgroup);


        return newPat != null && newPat.getId() != null;
    }




    @PatchMapping("patient/update-email/{id}")
    public ResponseEntity<Patientdto> updateEmail(@PathVariable Long id, @RequestParam String newEmail) {


        Optional<Patientdto> updatedPatient = patientService.updateEmail(id, newEmail);

        //  Agar patient mila aur update ho , toh response bhej  nahi hua toh 404 do
        if (updatedPatient.isPresent()) {
            return ResponseEntity.ok(updatedPatient.get()); // get func. lagana zaroori hai
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("patient/update-insurance/{id}")
    public ResponseEntity<Patientdto> updateInsurance(
            @PathVariable Long id,
            @RequestParam Long insuranceId) { //  Naye insurance ki ID le li

        // Service ko dono ids bhej di
        Optional<Patientdto> pdto = patientService.updateInsurance(id, insuranceId);

        if (pdto.isPresent()) {
            return ResponseEntity.ok(pdto.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }




    @DeleteMapping("patient/delete/{id}")
    public ResponseEntity<List<Patientdto>> deletePatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.deleteAndFetch(id));

    }


    @PutMapping("patient/email/{email}")
    public boolean updatePatientDetails(
            @PathVariable String email,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String bloodgroup,
            @RequestParam(required = false) Long insuranceId) {

        return patientService.updatePatientByEmail(email, gender, bloodgroup, insuranceId);
    }



}
