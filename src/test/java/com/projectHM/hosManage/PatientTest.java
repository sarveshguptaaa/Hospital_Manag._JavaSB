package com.projectHM.hosManage;

import com.projectHM.hosManage.Entities.BloodGroupCountResponseEntity;
import com.projectHM.hosManage.Entities.Patient;
import com.projectHM.hosManage.Repository.Patient_Repo;
import com.projectHM.hosManage.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class PatientTest {

    @Autowired
    private Patient_Repo patientRepo;

    @Autowired
    private PatientService patient_serv;

    @Test
    public void testPatientRepository() {
        List<Patient> patientList = patientRepo.findAll();
        System.out.println(patientList);
    }

    @Test
    public void testTransactionMethods() {
//        // 1. Pehle test ke andar hi ek fresh patient save karo
//        // Taaki test kisi bhi database (H2 ya Postgres) par chale, use data mil jaye
//        Patient sample = new Patient();
//        sample.setName("Sarvesh Gupta");
//        sample.setEmail("sarg62@gmail.com");
//        sample.setBirthdate(LocalDate.of(2002, 1, 28));
//        sample.setGender("Male");
//        Patient savedSample = patientRepo.save(sample); // Id generate hogi dynamically
//
//        // 2. Ab usi dynamically generated ID se fetch karo
//        Patient patient = patient_serv.getPatientById(savedSample.getId());
//        System.out.println("ID Se Mila Patient: " + patient);
//
//        // 3. Ab Birthdate aur Email se query karo (ab pakka milega!)
//        List<Patient> patientList = patientRepo.findByBirthdateOrEmail(LocalDate.of(2002, 2, 28), "febharsh2@gmail.com");
//
//        System.out.println("Miley hue patients ki sankhya: " + patientList.size());

        List<Patient> patientList = patientRepo.findByNameContainingIgnoreCase("rsh");
        for (Patient pat : patientList) {
            System.out.println("Query Match Data: " + pat);
        }
//reult of page isnsted of list
//        Hibernate:
//        select
//        p1_0.id,
//                p1_0.birthdate,
//                p1_0.bloodgroup,
//                p1_0.created_at,
//                p1_0.email,
//                p1_0.gender,
//                p1_0.patient_name
//        from
//        patient_tab p1_0
//        where
//        upper(p1_0.patient_name) like upper(?) escape '\'
//        Query Match Data: Patient{id=1, name='Harsh Gupta', birthdate=2002-02-28, email='harsh@gmail.com', gender='Male', bloodGroup='A', createdAt=2026-05-26T13:48:00.500954}
//        Query Match Data: Patient{id=4, name='Harsh Unique 1779783782157', birthdate=2002-02-28, email='harsh_unique@gmail.com', gender='Male', bloodGroup='B', createdAt=2026-05-26T13:53:02.180913}


        //result oflit using page inabove example
//
//        select
//        p1_0.id,
//                p1_0.birthdate,
//                p1_0.created_at,
//                p1_0.email,
//                p1_0.gender,
//                p1_0.patient_name
//        from
//        patient_tab p1_0
//        where
//        upper(p1_0.patient_name) like upper(?) escape '\'
//        Query Match Data: Patient{id=1, name='Harsh Gupta', birthdate=2002-02-28, email='harsh@gmail.com', gender='Male'}
//        Query Match Data: Patient{id=4, name='Harsh Unique 1779783782157', birthdate=2002-02-28, email='harsh_unique@gmail.com', gender='Male'}

    }

    @Test
    public void TransactionalJpaManualQuerymethods() {
        System.out.println("====== CUSTOM QUERY TEST STARTED ======");

        // Repository ka custom method call kiya
        List<Patient> patee = patientRepo.findByBloodGroup("B");

        System.out.println("Total 'B' group patients found: " + patee.size());

        for (Patient pats : patee) {
            System.out.println("BG OF PEOPLE IS: " + pats);
        }

        System.out.println("====== CUSTOM QUERY TEST ENDED ======");


        //result:_
//
//        Hibernate:
//        select
//        p1_0.id,
//                p1_0.birthdate,
//                p1_0.bloodgroup,
//                p1_0.created_at,
//                p1_0.email,
//                p1_0.gender,
//                p1_0.patient_name
//        from
//        patient_tab p1_0
//        where
//        p1_0.bloodgroup=?
//                Total 'B' group patients found: 2
//        BG OF PEOPLE IS: Patient{id=3, name='YOYO', birthdate=2002-02-28, email='febharsh2@gmail.com', gender='Male', bloodGroup='B', createdAt=2026-05-26T13:52:48.137169}
//        BG OF PEOPLE IS: Patient{id=4, name='Harsh Unique 1779783782157', birthdate=2002-02-28, email='harsh_unique@gmail.com', gender='Male', bloodGroup='B', createdAt=2026-05-26T13:53:02.180913}
    }

    @Test
    public void testUniqueConstraintException() {
        // Purane database state se bachne ke liye bilkul naya unique name use karte hain
        String uniqueName = "Harsh Unique " + System.currentTimeMillis();

        // 1. Pehla record save kiya
        Patient p1 = new Patient();
        p1.setName(uniqueName);
        p1.setEmail("harsh_unique@gmail.com");
        p1.setBirthdate(LocalDate.of(2002, 2, 28));
        p1.setGender("Male");
        patientRepo.save(p1);

        // 2. Same Name aur Birthdate ke sath duplicate record
        Patient p2 = new Patient();
        p2.setName(uniqueName);
        p2.setEmail("harsh_new_diff@gmail.com"); // Email unique hai par Name-Birthdate duplicate hai
        p2.setBirthdate(LocalDate.of(2002, 2, 28));
        p2.setGender("Male");

        // JUnit assertThrows check karega ki exception aa raha hai ya nahi
        org.junit.jupiter.api.Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            patientRepo.save(p2);
            patientRepo.flush(); // Force hibernate to push data immediately to catch exception
        });

        System.out.println("Unique constraint perfectly kaam kar raha hai aur duplicate block ho gaya!");
    }


    @Test
    public void testFindByNameQuery() {
        // This perfectly matches the List<Patient> return type in Patient_Repo
        List<Patient> patientList = patientRepo.findByName("Harsh Gupta");

        for (Patient patient : patientList) {
            System.out.println(patient);
        }


    }

    @Test
    public void TestAfterBirthJPAManualQuery() {
//        List<Patient> patientList = patientRepo.findByBornAfterDate(LocalDate.of(1999, 05, 18));

        List<Object[]> bloodGroupList = patientRepo.countEachBloodGroupTypessss();
        for (Object[] objects : bloodGroupList) {
            System.out.println(objects[0] + " " + objects[1]);
        }

//        result:-
//                Hibernate:
//        select
//        p1_0.bloodgroup,
//                count(p1_0.id)
//        from
//        patient_tab p1_0
//        group by
//        p1_0.bloodgroup
//        B 2
//        A 2


        //result:-
//    Hibernate:
//    select
//    p1_0.id,
//    p1_0.birthdate,
//    p1_0.bloodgroup,
//    p1_0.created_at,
//    p1_0.email,
//    p1_0.gender,
//    p1_0.patient_name
//            from
//    patient_tab p1_0
//    where
//    p1_0.birthdate>?
//    Patient{id=1, name='Harsh Gupta', birthdate=2002-02-28, email='harsh@gmail.com', gender='Male', bloodGroup='A', createdAt=2026-05-26T13:48:00.500954}
//    Patient{id=8, name='YOYO', birthdate=2002-01-28, email='sarg62@gmail.com', gender='Male', bloodGroup='A', createdAt=2026-05-26T13:57:36.241272}
//    Patient{id=3, name='YOYO', birthdate=2002-02-28, email='febharsh2@gmail.com', gender='Male', bloodGroup='B', createdAt=2026-05-26T13:52:48.137169}
//    Patient{id=4, name='Harsh Unique 1779783782157', birthdate=2002-02-28, email='harsh_unique@gmail.com', gender='Male', bloodGroup='B', createdAt=2026-05-26T13:53:02.180913}



        Page<Patient> patents = patientRepo.findAllPatients(PageRequest.of(0,2 ));
        for(Patient pats : patents){
            System.out.println(pats);
        }

//        Resut:-
//                Hibernate:
//        Select
//                *
//                from
//        patient_tab
//        2026-05-26T15:43:44.980+05:30 DEBUG 1920 --- [hosManage] [           main] org.hibernate.orm.results                : Disallowing positional selections: Select * from patient_tab
//        Patient{id=1, name='Harsh Gupta', birthdate=2002-02-28, email='harsh@gmail.com', gender='Male', bloodGroup='A', createdAt=2026-05-26T13:48:00.500954}
//        Patient{id=8, name='YOYO', birthdate=2002-01-28, email='sarg62@gmail.com', gender='Male', bloodGroup='A', createdAt=2026-05-26T13:57:36.241272}
//        Patient{id=3, name='YOYO', birthdate=2002-02-28, email='febharsh2@gmail.com', gender='Male', bloodGroup='B', createdAt=2026-05-26T13:52:48.137169}
//        Patient{id=4, name='Harsh Unique 1779783782157', birthdate=2002-02-28, email='harsh_unique@gmail.com', gender='Male', bloodGroup='B', createdAt=2026-05-26T13:53:02.180913}
    }

    @Test
    public void transactionUpdateNameWithId( ){

        int updateNameWithId = patientRepo.updateNameWithId("Aaarav singh", 3L);
        System.out.println(updateNameWithId);
    }

    @Test
    public void testBloodGroupResponse() {
        List<BloodGroupCountResponseEntity> list = patientRepo.countEachBloodGroupType();
        for (BloodGroupCountResponseEntity row : list) {
            System.out.println("Blood Group: " + row.getBloodGroupType() + " | Count: " + row.getCount());
        }
    }

    //res:-
//    Java HotSpot(TM) 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
//    Hibernate:
//    select
//    p1_0.bloodgroup,
//    count(p1_0.id)
//    from
//    patient_tab p1_0
//    group by
//    p1_0.bloodgroup
//    Blood Group: B | Count: 2
//    Blood Group: A | Count: 2



    @Test
    public void testPatientwithAppoinmentsavoidNplusone(){
        List<Patient> patlist = patientRepo.findAllPatientWithAppointment();

        // अब यह बिना किसी एरर के आराम से प्रिंट होगा
        patlist.forEach(patient -> {
            System.out.println("Patient: " + patient.getName());
            System.out.println("Appointments Count: " + patient.getAppointments().size());
        });
    }
}