-- STEP 1: Doctors ka data
INSERT INTO doctor (name, specialization, email)
VALUES
    ('Dr. Alok Sharma', 'Cardiologist', 'alok.sharma@hospital.com'),
    ('Dr. Priya Verma', 'Pediatrician', 'priya.verma@hospital.com'),
    ('Dr. Amit Gupta', 'Neurologist', 'amit.gupta@hospital.com'),
    ('Dr. Sneha Reddy', 'Dermatologist', 'sneha.reddy@hospital.com'),
    ('Dr. Vikram Malhotra', 'Orthopedic', 'vikram.m@hospital.com')
ON CONFLICT (email) DO NOTHING;




INSERT INTO patient_tab (id, patient_name, birthdate, email, gender, bloodgroup, blood_group_type)
VALUES
    (1, 'Sarvesh Gupta', '2003-06-09', 'sarvesh.gupta@email.com', 'MALE', 'O+', 'POSITIVE'),
    (2, 'Amit Kumar', '1996-03-15', 'amit.kumar@email.com', 'MALE', 'A+', 'POSITIVE'),
    (3, 'Rahul Verma', '1998-11-22', 'rahul.verma@email.com', 'MALE', 'B-', 'NEGATIVE')
ON CONFLICT (id) DO NOTHING;
--  Agar email par conflict aaye toh ON CONFLICT (email) DO NOTHING; bhi rakh sakte ho




--  Appointments ka data
INSERT INTO appointment (appointment_time, reason, patient_id, doctor_id)
VALUES
    (CAST('2026-06-01 10:00:00' AS TIMESTAMP), 'Regular body checkup and blood test', 1, 1),
    (CAST('2026-06-01 11:30:00' AS TIMESTAMP), 'Severe migraine and high blood pressure', 2, 2),
    (CAST('2026-06-02 14:15:00' AS TIMESTAMP), 'Follow-up for previous orthopedic surgery', 3, 1),
    (CAST('2026-06-03 09:00:00' AS TIMESTAMP), 'Sudden skin allergy and rashes', 1, 3),
    (CAST('2026-06-04 16:30:00' AS TIMESTAMP), 'Routine dental cleaning and consultation', 2, 4);