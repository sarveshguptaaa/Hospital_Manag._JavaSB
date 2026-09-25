package com.projectHM.hosManage.Controllers;



import com.projectHM.hosManage.dto.AppointmentRequestdto;
import com.projectHM.hosManage.dto.AppointmentRequestdto;
import com.projectHM.hosManage.dto.AppointmentResponsedto;
import com.projectHM.hosManage.dto.AppointmentResponsedto;
import com.projectHM.hosManage.service.appointmnetService;
import com.projectHM.hosManage.service.appointmnetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/appointments")
public class AppointmentController {

    private final appointmnetService appointmentService;

    public AppointmentController(appointmnetService appointmentService) {
        this.appointmentService = appointmentService;
    }



    @PostMapping("/book")
    public ResponseEntity<AppointmentResponsedto> bookAppointment(@Valid @RequestBody AppointmentRequestdto request) {

        AppointmentResponsedto response = appointmentService.bookAppointment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponsedto>> getAppointmentsByPatient(@PathVariable Long patientId) {

        List<AppointmentResponsedto> list = appointmentService.getAppointmentsByPatient(patientId);

        return ResponseEntity.ok(list);
    }




    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentResponsedto>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        List<AppointmentResponsedto> list = appointmentService.getAppointmentsByDoctor(doctorId);
        return ResponseEntity.ok(list);
    }

   //http://localhost:8080/api/appointments/1/status?status=COMPLETED
    @PutMapping("/{id}/status")
    public ResponseEntity<AppointmentResponsedto> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return appointmentService.updateStatus(id, status)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        boolean isDeleted = appointmentService.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.ok("Appointment deleted successfully with ID: " + id);
        }
        return ResponseEntity.notFound().build();
    }
}
