package com.projectHM.hosManage.Entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false , length = 100)
    private String name;

    @Column(length = 100)
    private String specialization;

    @Column(nullable= false , length= 100 , unique= true)
    private String email;

    @ManyToMany(mappedBy = "doctors") // ha mapped by lagan ajajruir h kuiki nai table banajayegi dobara joint table ki jo alrready Deprtment table k samy ban gyi h
   private Set<Department> departments = new HashSet<>();


    // Inside Doctor.java
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    @JsonManagedReference  // <-- Ensure this is present in Doctor entity!
    private List<Appointment> appointments;



}
