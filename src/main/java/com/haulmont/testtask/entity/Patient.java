package com.haulmont.testtask.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Patient")
public class Patient {
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
    @NotNull(message = "Phone cannot be empty!")
    @Pattern(regexp = "^[1-9]\\d{10}$")
    private String phoneNum;

    @OneToMany(mappedBy = "Patient",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH)
    private List<Prescription> prescriptions;

    public Patient() {

    }

    public Patient(@NotNull String firstName, @NotNull String middleName,
                   @NotNull String lastName, @NotNull String phoneNum) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(@NotNull String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "Patient: " + "id = " + id + " |" +
                " First name = " + firstName + " |" +
                " Middle name = " + middleName + " |" +
                " Last name = " + lastName + " |" +
                " Phone number = " + phoneNum + " |" +
                " Prescriptions" + prescriptions +" |\n";
    }
}
