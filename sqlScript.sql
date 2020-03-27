CREATE TABLE Doctor (
  id BIGINT NOT NULL,
  firstname VARCHAR(50) NOT NULL,
  middlename VARCHAR(100) NOT NULL,
  lastname VARCHAR(50) NOT NULL,
  specialization VARCHAR(30) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Patient (
   id BIGINT NOT NULL,
   firstname VARCHAR(50) NOT NULL,
   middlename VARCHAR(100) NOT NULL,
   lastname VARCHAR(50) NOT NULL,
   phonenum VARCHAR(15) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE Prescription (
   id BIGINT NOT NULL,
   description VARCHAR(2000) NOT NULL,
   priority VARCHAR(15) NOT NULL,
   validity DATE NOT NULL,
   createdate DATE NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_doctor_id
    FOREIGN KEY (doctor_id)
    REFERENCES DOCTOR
  CONSTRAINT fk_patient_id
    FOREIGN KEY (patient_id)
    REFERENCES PATIENT
);
