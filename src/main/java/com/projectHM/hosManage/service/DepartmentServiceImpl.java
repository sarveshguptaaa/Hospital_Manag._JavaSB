package com.projectHM.hosManage.service;

import com.projectHM.hosManage.Entities.Department;
import com.projectHM.hosManage.Entities.Doctor;
import com.projectHM.hosManage.Repository.DepartmentRepository;
import com.projectHM.hosManage.Repository.DoctorRepo;
import com.projectHM.hosManage.dto.Departmentdto;
import com.projectHM.hosManage.service.DepartmentServiceInt;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentServiceInt {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DoctorRepo doctorRepository;

    @Override
    @Transactional
    public Departmentdto createDepartment(Departmentdto dto) {
        Department department = new Department();
        department.setDname(dto.getDname());

        if (dto.getHeadDoctorId() != null) {
            Doctor headDoctor = doctorRepository.findById(dto.getHeadDoctorId())
                    .orElseThrow(() -> new RuntimeException("Head Doctor not found with ID: " + dto.getHeadDoctorId()));
            department.setHeaddoctor(headDoctor);
        }

        if (dto.getDoctorIds() != null && !dto.getDoctorIds().isEmpty()) {
            Set<Doctor> doctors = new HashSet<>(doctorRepository.findAllById(dto.getDoctorIds()));
            department.setDoctors(doctors);
        }

        Department savedDepartment = departmentRepository.save(department);
        return mapTodto(savedDepartment);
    }

    @Override
    public Departmentdto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
        return mapTodto(department);
    }

    @Override
    public List<Departmentdto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapTodto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Departmentdto assignDoctorToDepartment(Long departmentId, Long doctorId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + departmentId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + doctorId));

        department.getDoctors().add(doctor);
        Department updatedDepartment = departmentRepository.save(department);
        return mapTodto(updatedDepartment);
    }

    @Override
    @Transactional
    public Departmentdto assignHeadDoctor(Long departmentId, Long doctorId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + departmentId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + doctorId));

        department.setHeaddoctor(doctor);
        Department updatedDepartment = departmentRepository.save(department);
        return mapTodto(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with ID: " + id);
        }
        departmentRepository.deleteById(id);
    }

    // Helper Mapper Method
    private Departmentdto mapTodto(Department department) {
        Set<Long> doctorIds = department.getDoctors() != null
                ? department.getDoctors().stream().map(Doctor::getId).collect(Collectors.toSet())
                : new HashSet<>();

        return Departmentdto.builder()
                .id(department.getId())
                .dname(department.getDname())
                .headDoctorId(department.getHeaddoctor() != null ? department.getHeaddoctor().getId() : null)
                .doctorIds(doctorIds)
                .build();
    }
}

