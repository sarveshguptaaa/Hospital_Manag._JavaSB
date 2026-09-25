//package com.projectHM.hosManage.Entities;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.DynamicUpdate;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Entity
//@DynamicUpdate
//@Table(
//    name = "patient_tab",
//        uniqueConstraints = {
//            @UniqueConstraint(name = "unique_patient_email", columnNames = {"email"}),
//                @UniqueConstraint(name = "unique_patient_name_birthdate", columnNames = {"patient_name", "birthdate"})
//        },
//        indexes = {
//            @Index(name = "idx_patient_birthdate", columnList ="birthdate")
//        }
//        )
//
//@Getter
//@Setter
//public class Patient {
//
//    @Override
//    public String toString() {
//        return "Patient{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", birthdate=" + birthdate +
//                ", email='" + email + '\'' +
//                ", gender='" + gender + '\'' +
//                ", bloodGroup='" + bloodGroup + '\'' +
//                ", createdAt=" + createdAt +
//                '}';
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name ="patient_name", nullable = false, length = 40)
//    private String name;
//    private LocalDate birthdate;
//
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//     private String gender;
//
//    @Column(name = "bloodgroup") // Yeh line Hibernate ko 'blood_group' dhoondhne se rokeg
//     private String bloodGroup;
//
//     @CreationTimestamp
//     @Column(updatable = false)
//     private LocalDateTime createdAt;
//
//
//}
//
////result using @Table:-
//// Hibernate:
////    create index idx_patient_birthdate
////       on patient_tab (birthdate)
////alwys creating new table new column without destroying previous one


package com.projectHM.hosManage.Entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicUpdate
@Table(
        name = "patient_tab",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_patient_email", columnNames = {"email"}),
                @UniqueConstraint(name = "unique_patient_name_birthdate", columnNames = {"patient_name", "birthdate"})
        },
        indexes = {
                @Index(name = "idx_patient_birthdate", columnList ="birthdate")
        }
)

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBloodGroupType() {
        return bloodGroupType;
    }

    public void setBloodGroupType(String bloodGroupType) {
        this.bloodGroupType = bloodGroupType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Column(name ="patient_name", nullable = false, length = 40)
    private String name;

    private LocalDate birthdate;

    @Column(unique = true, nullable = false)
    private String email;

    private String gender;

    @Column(name = "bloodgroup")
    private String bloodGroup;


    private String bloodGroupType;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // --- MANUAL GETTERS & SETTERS (Lombok ki chutti) ---




//    @OneToOne  //owning therelationship side
//  @JoinColumn(name= "patient_insurance_id")
//@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
@OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
@JoinColumn(name = "insurance_id", referencedColumnName = "id")
@JsonManagedReference
private Insurance insurance;  //ye krte hui association patient table au rinsurance table k sth create ho jayega
    //ye insurance_id nam se colmn banega aur ye k join column ..



//
//   @OneToMany(mappedBy = "patient", fetch= FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
//   @JsonManagedReference
//   private List<Appointment>appointments = new ArrayList<>();



    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference // Parent side par Managed
    private List<Appointment> appointments = new ArrayList<>();
}