package com.projectHM.hosManage.Controllers;


import com.projectHM.hosManage.Entities.Insurance;
import com.projectHM.hosManage.dto.Insurancedto;
import com.projectHM.hosManage.service.InsuranceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InsuranceController {


    public InsuranceController(InsuranceService insureserv) {
        this.insureserv = insureserv;
    }

    private final InsuranceService insureserv;

    @GetMapping("/insuranceall")
    public ResponseEntity<List<Insurance>> getAllInsurance() {
        return ResponseEntity.ok(insureserv.findAll());
    }

    @GetMapping("/insurance/id/{id}")
    public ResponseEntity<Insurance> findById(@PathVariable Long id) {

        Insurance insurance = insureserv.findById(id);

        return ResponseEntity.ok(insurance);
    }

    @GetMapping("/insurance/policy/{policyNumber}")
    public ResponseEntity<Insurance> findByPolicyNumber(
            @PathVariable String policyNumber) {

        Insurance insurance = insureserv.findByPolicyNumber(policyNumber);

        return ResponseEntity.ok(insurance);
    }



    @PostMapping(
            value = "/insurance/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Insurance> createInsurance(@Valid @RequestBody Insurancedto dto) {
        Insurance savedInsurance = insureserv.saveInsuranceFromDto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInsurance);
    }


//    @PutMapping("/insurance/update-id/{policyNumber}/{newId}")
//    public ResponseEntity<String> updateId(
//            @PathVariable String policyNumber,
//            @PathVariable Long newId) {
//
//
//        try {
//
//            String message = insureserv.updateId(policyNumber, newId);
//            return ResponseEntity.ok(message);
//        } catch (RuntimeException e) {
//            // Agar id nahi mili aur service ne exception throw ki, toh yahan handle ho jayega
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }

    @PutMapping("/insurance/{policyNumber}")
    public ResponseEntity<String> updateProvider(
            @PathVariable String policyNumber,
            @RequestBody Insurancedto insuredto) {

        boolean isUpdated = insureserv.updateProvider(policyNumber, insuredto);

        if (isUpdated) {
            return ResponseEntity.ok("Successfully updated");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/insurance/{policyNumber}")
    public ResponseEntity<?> deleteByPolicyNumber(@PathVariable String policyNumber) {

       List<Insurance> insures = insureserv.findAll();
        boolean found = false;

        for (int i = insures.size() - 1; i >= 0; i--) {
            if (policyNumber.equals(insures.get(i).getPolicyNumber())) {
                insures.remove(i); // List se remove kiya
                found = true;

                // insuranceRepository.delete(insures.get(i));
            }
        }

        if (found) {
            return ResponseEntity.ok("Deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}
