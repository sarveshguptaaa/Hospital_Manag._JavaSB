# Hospital Management System

A backend Hospital Management System built using **Java and Spring Boot** to manage patients, doctors, departments, appointments, and insurance information through REST APIs.

## 🚀 Features

* Manage patient information
* Manage doctor information
* Manage hospital departments
* Schedule and manage appointments
* Manage patient insurance details
* RESTful APIs for CRUD operations
* Entity relationships using JPA and Hibernate
* PostgreSQL database integration
* Exception handling and validation
* JSON-based API responses

## 🛠️ Technologies Used

* **Java**
* **Spring Boot**
* **Spring Data JPA**
* **Hibernate**
* **PostgreSQL**
* **REST APIs**
* **Maven**
* **Lombok**
* **Postman**

## 📌 Main Entities

### Patient

Stores patient information such as patient details and their associated insurance and appointments.

### Doctor

Stores doctor information and their association with departments and appointments.

### Department

Manages hospital departments and the doctors working under each department.

### Appointment

Handles appointment details between patients and doctors, including appointment date and time.

### Insurance

Stores insurance-related information associated with patients.

## 🏗️ Project Architecture

```text
Client / Postman
       |
       v
REST Controller
       |
       v
Service Layer
       |
       v
Repository Layer
       |
       v
JPA / Hibernate
       |
       v
PostgreSQL Database
```

## 🔗 Entity Relationships

```text
              +-------------+
              | Department  |
              +-------------+
                    |
                    | 1 : Many
                    v
              +-------------+
              |   Doctor    |
              +-------------+
                    |
                    | 1 : Many
                    v
              +-------------+
              | Appointment |
              +-------------+
                 ^       ^
                 |       |
              Many:1   Many:1
                 |       |
          +-------------+
          |   Patient   |
          +-------------+
                 |
                 | 1 : 1
                 v
          +-------------+
          |  Insurance  |
          +-------------+
```

## 📂 Project Structure

```text
src
 └── main
      └── java
           └── ...
                ├── controller
                ├── service
                ├── repository
                ├── entity
                └── exception
```

## ⚙️ Setup & Installation

### 1. Clone the repository

```bash
git clone <your-repository-url>
```

### 2. Open the project

Open the project in **IntelliJ IDEA**.

### 3. Configure PostgreSQL

Create a PostgreSQL database and update the database configuration in:

```text
application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Run the application

Run the Spring Boot application from IntelliJ IDEA.

The APIs can then be tested using **Postman**.

## 🧪 API Testing

The REST APIs can be tested using Postman for operations such as:

* Create patient
* Get patient
* Update patient
* Delete patient
* Create doctor
* Get doctor
* Manage departments
* Create appointments
* Manage insurance information

## 🎯 Learning Outcomes

Through this project, I worked with:

* Spring Boot REST API development
* JPA and Hibernate
* Entity relationships and mappings
* Repository and service layers
* PostgreSQL database integration
* CRUD operations
* API testing using Postman
* Exception handling
* Backend application architecture

## Some Examples of Responses 


## Appointments
{
"appointmentId": 101,
"patientId": 36,
"patientName": "Rahul Verma",
"doctorId": 362,
"doctorName": "Dr. A. K. Sharma",
"appointmentTime": "2026-10-15T10:30:00",
"reason": "Routine Dental Checkup",
"status": "SCHEDULED"
}


## Patient resposne

{
"id": 36,
"name": "Rahul Verma",
"email": "rahul.newemail@gmail.com",
"phone": "9123456789",
"birthdate": "1998-05-15",
"insuranceId": 101,
"gender": "MALE",
"bloodgroup": "O_POSITIVE"
}

## Department Response 

{
"departmentId": 12,
"departmentName": "Cardiology",
"buildingWing": "Block-B, 3rd Floor",
"contactExtension": "402",
"headDoctorName": "Dr. A. K. Sharma",
"totalDoctors": 8,
"activeBeds": 45,
"status": "ACTIVE",
"createdAt": "2026-09-25T12:56:00"
}


## Doctor Response

{
"id": 363,
"name": "Dr. A. K. Sharma",
"specialization": "Senior Cardiologist",
"email": "dr.sharma.updated@hospital.com",
"phone": "9876543210"
}

## Insurance Response 

{
"insuranceId": 101,
"policyNumber": "STAR-HOSP-2026-9876",
"providerName": "Star Health Insurance",
"coverageAmount": 500000.00,
"remainingBalance": 450000.00,
"validUntil": "2028-12-31",
"verificationStatus": "VERIFIED",
"associatedPatientsCount": 3
}








## 👨‍💻 Author

**Sarvesh Gupta**

Java Developer | Spring Boot | REST APIs | SQL
