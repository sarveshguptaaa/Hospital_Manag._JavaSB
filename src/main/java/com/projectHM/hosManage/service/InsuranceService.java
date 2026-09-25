package com.projectHM.hosManage.service;

import com.projectHM.hosManage.Entities.Insurance;
import com.projectHM.hosManage.Entities.Patient;
import com.projectHM.hosManage.Repository.InsuranceRepository;
import com.projectHM.hosManage.Repository.Patient_Repo;
import com.projectHM.hosManage.dto.Insurancedto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsuranceService {


    private final InsuranceRepository insuranceRepository;

    private final Patient_Repo patientrepo;


//    @Transactional   //iski wajh se pe=atient persistence context mae rage phor ya toh sar esare iperation perform honge ya sar rollback hjynege
//    public Patient assignInsuranceToPatient(Insurance insurance , Long patient_id){
//
//        Patient patient = patientrepo.findById(patient_id)  //this patient under persistence context and patiebt get dirty as inusurance bhi dladiya h
//                .orElseThrow(()-> new EntityNotFoundException("Patients id nt found"+patient_id));
//
//
//        patient.setInsurance(insurance);//yha durty kra h patient ko //Kuiki patient owning side mae h agr bat Patient or Insurance ki h toh
//                                         //is lineki wajhh se agr insurance filed nhi higi database phele use banaya jayega phir insurance set krdiya jayega
//        insurance.setPatient(patient); //bidirectional consistecy
//
//        return patientrepo.saveAndFlush(patient);
//    }


    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patient_id) {

        Patient patient = patientrepo.findById(patient_id)
                .orElseThrow(() -> new EntityNotFoundException("Patients id nt found " + patient_id));

        //  CHANGE HERE: save() ki jagah saveAndFlush() use karo

        Insurance savedInsurance = insuranceRepository.saveAndFlush(insurance);

        // Ab relationship map karo
        patient.setInsurance(savedInsurance);
        savedInsurance.setPatient(patient);

        // Final save aur flush
        return patientrepo.saveAndFlush(patient);
    }








    @Transactional
    public boolean updateProvider(String policyNumber, Insurancedto insuresdto) {

        Optional<Insurance> optionalInsurance =
                insuranceRepository.findByPolicyNumber(policyNumber);

        if (optionalInsurance.isPresent()) {

            Insurance existingInsurance = optionalInsurance.get();

            existingInsurance.setProvider(insuresdto.getProvider());

            insuranceRepository.save(existingInsurance);
            return true;
        }

        return false;
    }


//    @Transactional
//    public String updateId(String policyNumber, Long newId) {
//
//        Insurance insurance = insuranceRepository.findByPolicyNumber(policyNumber)
//                .orElseThrow(() -> new RuntimeException("Bhai, ye insurance policy nahi mili: " + policyNumber));
//
//        Patient newPatient = patientrepo.findById(newId)
//                .orElseThrow(() -> new RuntimeException("Bhai, patient nahi mila id: " + newId));
//
//        // 3. Purane patient se rishta todoo
//        if (insurance.getPatient() != null) {
//            Patient oldPatient = insurance.getPatient();
//            oldPatient.setInsurance(null);
//            patientrepo.save(oldPatient);
//        }
//
//        // 4. Naye Patient ke sath bidirectional link jod do
//        insurance.setPatient(newPatient);
//        newPatient.setInsurance(insurance);
//
//        // 5. Patient Owning side hai, toh use save karo
//        patientrepo.save(newPatient);
//
//        // Jab sab sahi ho jaye toh mast message bhejo jo Postman mein dikhe
//        return "Bhai, Policy " + policyNumber + " perfectly set ho gayi hai Patient ID " + newId + " ke liye!";
//    }

    public Insurance saveinsures(Insurance insurance) {


        Long patientId = insurance.getPatient().getId();

        Patient patient = patientrepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient nahi mila bhai id: " + patientId));


        patient.setInsurance(insurance);

        //  Aur insurance ke andar bhi bidirectional relation ke liye patient set kar ah yaha
        insurance.setPatient(patient);

        // Ab kyunki Patient par CascadeType.ALL laga hai, hum Patient ko save karenge
        // toh Insurance apne aap database mein save ho jayegi aur foreign key bhi update ho jayegi!
        Patient savedPatient = patientrepo.save(patient);
        return savedPatient.getInsurance();
    }


    public Insurance findByPolicyNumber(String policyNumber) {
        return insuranceRepository.findByPolicyNumber(policyNumber)
                .orElseThrow(() -> new RuntimeException("Insurance not found"));
    }

    public Insurance findById(Long id) {
        return insuranceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insurance not found"));
    }

    public List<Insurance> findAll() {

        return insuranceRepository.findAll();
    }


    @Transactional
    public Insurance saveInsuranceFromDto(Insurancedto dto) {
        Insurance insurance = Insurance.builder()
                .policyNumber(dto.getPolicyNumber())
                .provider(dto.getProvider())
                .validUntill(dto.getValidUntill())
                .payment(dto.getPayment())
                .build();

//        if (dto.getPatientId() != null) {
//            Patient patient = patientRepo.findById(dto.getPatientId())
//                    .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + dto.getPatientId()));
//            insurance.setPatient(patient);
//        }

        return insuranceRepository.save(insurance);
    }
}
