

package com.projectHM.hosManage.dto;

import lombok.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Departmentdto {

    private Long id;
    private String dname;
    private Long headDoctorId;
    private Set<Long> doctorIds;
}