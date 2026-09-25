package com.projectHM.hosManage.Controllers;


import com.projectHM.hosManage.dto.Departmentdto;

import com.projectHM.hosManage.service.DepartmentServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartementController {


    @Autowired
    private DepartmentServiceInt departmentService;


    @PostMapping("/create")
    public ResponseEntity<Departmentdto> createDepartment(@RequestBody Departmentdto departmentDTO) {
        Departmentdto created = departmentService.createDepartment(departmentDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Departmentdto> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }


    @GetMapping
    public ResponseEntity<List<Departmentdto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }


    // Assign Doctor to Department (dep_doctor join table me entry add kri h
    @PutMapping("/{deptId}/doctors/{doctorId}")
    public ResponseEntity<Departmentdto> assignDoctor(
            @PathVariable Long deptId,
            @PathVariable Long doctorId) {
        return ResponseEntity.ok(departmentService.assignDoctorToDepartment(deptId, doctorId));
    }

  // assign head_doctor
    @PutMapping("/{deptId}/head-doctor/{doctorId}")
    public ResponseEntity<Departmentdto> assignHeadDoctor(
            @PathVariable Long deptId,
            @PathVariable Long doctorId) {
        return ResponseEntity.ok(departmentService.assignHeadDoctor(deptId, doctorId));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Department deleted successfully with ID: " + id);
    }
}