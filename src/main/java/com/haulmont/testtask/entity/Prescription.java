package com.haulmont.testtask.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "Prescription" )
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Size(min = 1, max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @NotNull @Column(name = "createdate")
    private LocalDate createDate;

    @NotNull @Column(name = "validity")
    private LocalDate validity;

    @NotNull @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 15)
    private Priority priority;

    public Prescription() {}

    public Prescription(@NotNull String description,  Patient patient, Doctor doctor,
                        LocalDate createDate, LocalDate validity, @NotNull Priority priority) {
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.createDate = createDate;
        this.validity = validity;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getValidity() {
        return validity;
    }

    public void setValidity(LocalDate validity) {
        this.validity = validity;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Prescription: " + "id = " + id + " |" +
                " Description = " + description + " |" +
                " Patient = " + patient + " |" +
                " Doctor = " + doctor + " |" +
                " Date of create = " + createDate + " |" +
                " Validity = " + validity + " |" +
                " Priority = " + priority + " |\n";
    }
}
