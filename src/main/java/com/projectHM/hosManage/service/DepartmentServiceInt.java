package com.projectHM.hosManage.service;

import com.projectHM.hosManage.dto.Departmentdto;

import java.util.List;

public interface DepartmentServiceInt {

    Departmentdto createDepartment(Departmentdto dto);
    Departmentdto getDepartmentById(Long id);
    List<Departmentdto> getAllDepartments();
    Departmentdto assignDoctorToDepartment(Long departmentId, Long doctorId);
    Departmentdto assignHeadDoctor(Long departmentId, Long doctorId);
    void deleteDepartment(Long id);
}

