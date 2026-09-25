package com.projectHM.hosManage.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dname;

    @OneToOne
    @JoinColumn(name = "head_doctor_id")
    private Doctor headdoctor;

    @Builder.Default // Builder use krte h null reference se bachne ke liye
    @ManyToMany
    @JoinTable(
            name = "dep_doctor",
            joinColumns = @JoinColumn(name = "dpt_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private Set<Doctor> doctors = new HashSet<>();
}