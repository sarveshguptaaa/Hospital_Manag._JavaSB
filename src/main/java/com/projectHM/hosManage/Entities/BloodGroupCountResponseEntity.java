package com.projectHM.hosManage.Entities;

public class BloodGroupCountResponseEntity {

    private String bloodGroupType;
    private Long count;

    //  Manual NoArgs Constructor
    public BloodGroupCountResponseEntity() {
    }

    // Manual All-Args Constructor (Yeh JPQL Query ke 'new' expression ke liye ZAROORI hai)
    public BloodGroupCountResponseEntity(String bloodGroupType, Long count) {
        this.bloodGroupType = bloodGroupType;
        this.count = count;
    }


    public String getBloodGroupType() {
        return bloodGroupType;
    }

    public void setBloodGroupType(String bloodGroupType) {
        this.bloodGroupType = bloodGroupType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}