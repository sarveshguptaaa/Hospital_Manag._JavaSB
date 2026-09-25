package com.projectHM.hosManage;


import com.projectHM.hosManage.Entities.Appointment;
import com.projectHM.hosManage.Entities.Insurance;
import com.projectHM.hosManage.Entities.Patient;
import com.projectHM.hosManage.Repository.InsuranceRepository;
import com.projectHM.hosManage.Repository.Patient_Repo;
import com.projectHM.hosManage.service.InsuranceService;
import com.projectHM.hosManage.service.PatientService;
import com.projectHM.hosManage.service.appointmnetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class Insurancetestt {


    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private InsuranceRepository insures ;

    @Autowired
    private appointmnetService appointmentService;

    @Autowired
    private Patient_Repo patientRepo;

    @Test
    public void checkupdateonPatientByInsuranceInsert() {


        Insurance insure = Insurance.builder()
                .policyNumber("ICICI_101")
                .provider("ICICI")
                .validUntill(LocalDate.of(2027, 01, 04))
                .build();  //jb ise call lrkenge toh khud ba khu ddatabase mae ye sb chez save hongi ar baci hui fieldbhi create ho jynegei

        Patient patient = insuranceService.assignInsuranceToPatient(insure, 1L);
        System.out.println(patient);
        Insurance savedInsurance = insures.saveAndFlush(insure);

//        output:-
//        Hibernate:
//        select
//        p1_0.id,
//                p1_0.birthdate,
//                p1_0.bloodgroup,
//                p1_0.blood_group_type,
//                p1_0.created_at,
//                p1_0.email,
//                p1_0.gender,
//                i1_0.id,
//                i1_0.created_at,
//                i1_0.policy_number,
//                i1_0.provider,
//                i1_0.valid_untill,
//                p1_0.patient_name
//        from
//        patient_tab p1_0
//        left join
//        insurance i1_0
//        on i1_0.id=p1_0.insurance_id
//        where
//        p1_0.id=?
//                Hibernate:
//                insert
//        into
//                insurance
//        (created_at, policy_number, provider, valid_untill)
//        values
//                (?, ?, ?, ?)
//        Hibernate:
//        update
//                patient_tab
//        set
//        insurance_id=?
//                where
//        id=?
// parent patent h aur childeren Insurance oth CASCADING ki wajhse Insurance bhi bana aur uodate bhi hua
        //in shirt parent uodate so all operation in child alsoget uopdate
        //in JPa domain ..Owning h:- Insurance , Inverse :- Patient
        //in data domain ..Parent :- patient , child :- Insurance


    }

    @Test
    public void testcreateAppointmnet() {
        Appointment appointment = Appointment.builder()
                .appointmentTime(LocalDateTime.of(2026, 06, 24, 01, 23))
                .reason("Cancer")
                .build();

        var newAppointment = appointmentService.createdAppointmentbyId(appointment, 1l, 1l);

        System.out.println(newAppointment);

    var updatedappointed = appointmentService.reAssignAppointmmetToOtherDr(newAppointment.getId(), 3L);

        System.out.println(updatedappointed);
    }


//    Hibernate:
//    insert
//            into
//    appointment
//            (appointment_time, doctor_id, patient_id, reason)
//    values
//            (?, ?, ?, ?)
//    Appointment(id=3, appointmentTime=2026-06-24T01:23, reason=Cancer, patient=com.projectHM.hosManage.Entities.Patient@4df25105, doctor=com.projectHM.hosManage.Entities.Doctor@eefc1bf)
//    Hibernate:
//    select
//    a1_0.id,
//    a1_0.appointment_time,
//    a1_0.doctor_id,
//    d1_0.id,
//    d1_0.email,
//    d1_0.name,
//    d1_0.specialization,
//    a1_0.patient_id,
//    p1_0.id,
//    p1_0.birthdate,
//    p1_0.bloodgroup,
//    p1_0.blood_group_type,
//    p1_0.created_at,
//    p1_0.email,
//    p1_0.gender,
//    i1_0.id,
//    i1_0.created_at,
//    i1_0.policy_number,
//    i1_0.provider,
//    i1_0.valid_untill,
//    p1_0.patient_name,
//    a1_0.reason
//            from
//    appointment a1_0
//    join
//    doctor d1_0
//    on d1_0.id=a1_0.doctor_id
//            join
//    patient_tab p1_0
//    on p1_0.id=a1_0.patient_id
//    left join
//    insurance i1_0
//    on i1_0.id=p1_0.insurance_id
//            where
//    a1_0.id=?
//    Hibernate:
//    select
//    d1_0.id,
//    d1_0.email,
//    d1_0.name,
//    d1_0.specialization
//            from
//    doctor d1_0
//    where
//    d1_0.id=?
//    Hibernate:
//    update
//            appointment
//    set
//    appointment_time=?,
//    doctor_id=?,
//    patient_id=?,
//    reason=?
//    where
//    id=?
//    Appointment(id=3, appointmentTime=2026-06-24T01:23, reason=Cancer, patient=com.projectHM.hosManage.Entities.Patient@6c00f72a, doctor=com.projectHM.hosManage.Entities.Doctor@62d26820)
//2026-05-30T20:21:14.104+05:30  INFO 23452 --- [hosManage] [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
//            2026-05-30T20:21:14.107+05:30  INFO 23452 --- [hosManage] [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
//            2026-05-30T20:21:14.110+05:30  INFO 23452 --- [hosManage] [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
//
//    Process finished with exit code 0


    @Test
    public void testDessociatePateintfromInsurance() {
        // Har run ke liye ek unique timestamp le rahe hain
        long currentTime = System.currentTimeMillis();

        // 1. Fresh Patient banaya aur UNIQUE naam aur email diya
        Patient dummyPatient = new Patient();
        dummyPatient.setName("Patient_" + currentTime); // Har baar naam badal jayega (e.g., Patient_1718378...)
        dummyPatient.setEmail("test.patient." + currentTime + "@hospital.com"); // Email bhi unique rahega
        Patient savedPatient = patientRepo.save(dummyPatient);

        // 2. Fresh Insurance object banaya aur iski policy number bhi unique rakhi
        Insurance insure = new Insurance();
        insure.setPolicyNumber("POL_" + currentTime); // Policy number bhi har baar alag hoga
        insure.setProvider("LIC");
        insure.setValidUntill(LocalDate.now().plusYears(1));

        // 3. Direct service ko call karo (Naya updated object fetch hoga)
        Patient updatedPatientWithInsurance = insuranceService.assignInsuranceToPatient(insure, savedPatient.getId());

        // Sanity check: Pata chal sake ki link hua ya nahi
        org.junit.jupiter.api.Assertions.assertNotNull(updatedPatientWithInsurance.getInsurance(), "Pehle step me Insurance assign hi nahi hui!");
        Long patientId = updatedPatientWithInsurance.getId();

        // 4. Ab dissociation test karo (Naye updated ID se)
        Patient dissociatedPatient = insuranceService.dissassociatefromInsurance(patientId);

        // Final Assertions
        org.junit.jupiter.api.Assertions.assertNotNull(dissociatedPatient, "Dissociated patient object null aa rha hai!");
        org.junit.jupiter.api.Assertions.assertNull(dissociatedPatient.getInsurance(), "Insurance abhi bhi link hai, dissociate nahi hui!");

        System.out.println("Success! Test complete for Patient ID: " + patientId);
    }




}




