package com.haulmont.testtask.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "First name cannot be empty!")
    @Size(min = 1)
    private String firstName;

    @NotNull(message = "Middle name cannot be empty!")
    @Size(min = 1)
    private String middleName;

    @NotNull(message = "Last name cannot be empty!")
    @Size(min = 1)
    private String lastName;

    @NotNull(message = "Specialization name cannot be empty!")
    @Size(min = 1)
    private String specialization;

    @OneToMany(mappedBy = "Doctor",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH)
    private List<Prescription> prescriptions;

    public Doctor() {}

    public Doctor(@NotNull String firstName, @NotNull String middleName,
                  @NotNull String lastName, @NotNull String specialization) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(@NotNull String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(@NotNull String specialization) {
        this.specialization = specialization;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    @Override
    public String toString() {
        return "Doctor: " + "id = " + id + " |" +
                " First name = " + firstName + " |" +
                " Middle name = " + middleName + " |" +
                " Last name = " + lastName + " |" +
                " Specialization = " + specialization + " |" +
                " Prescriptions" + prescriptions +" |\n";
    }
}
