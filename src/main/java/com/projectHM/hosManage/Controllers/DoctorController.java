package com.projectHM.hosManage.Controllers;

import com.projectHM.hosManage.Entities.Doctor;
import com.projectHM.hosManage.Repository.DoctorRepo;
import com.projectHM.hosManage.dto.Doctordto;
import com.projectHM.hosManage.service.DoctorServiice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorServiice doctorServ;

    @GetMapping("/")
    public String home() {
        return "Home";
    }


    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorServ.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {

        Optional<Doctor> doctor = doctorServ.findById(id);

        if (doctor.isEmpty()) {
            return ResponseEntity.badRequest().body("Doctor not found");
        }

        return ResponseEntity.ok(doctor.get());
    }


    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDoc(@RequestBody Doctordto doctorDto) {

        if (doctorServ.findByEmail(doctorDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Doctor already exists");
        }

        // Conv. DTO to Entity
        Doctor doctor = new Doctor();
        doctor.setName(doctorDto.getName());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setSpecialization(doctorDto.getSpecialization());

        Doctor savedDoctor = doctorServ.save(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }


    //http://localhost:8080/doctor/update/1

    @PutMapping(value = "/update/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctor doctor) {

        try {
            Doctor updatedDoctor = doctorServ.updateDoctor(id, doctor);
            return ResponseEntity.ok(updatedDoctor);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDoc(@PathVariable Long id) {

        Optional<Doctor> doctor = doctorServ.findById(id);

        if (doctor.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Doctor not found");
        }

        doctorServ.deleteById(id);

        return ResponseEntity.ok("Doctor deleted successfully");
    }
}