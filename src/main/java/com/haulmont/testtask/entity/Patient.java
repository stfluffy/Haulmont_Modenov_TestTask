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

    @NotNull() @Size(min = 1)
    @Column(name = "firstname", length = 50)
    private String firstName;

    @NotNull() @Size(min = 1)
    @Column(name = "middlename", length = 100)
    private String middleName;

    @NotNull() @Size(min = 1)
    @Column(name = "lastname", length = 50)
    private String lastName;

    @NotNull() @Pattern(regexp = "^[1-9]\\d{10}$")
    @Column(name = "phonenum", length = 15)
    private String phoneNum;

    @OneToMany(mappedBy = "patient",
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    @Override
    public String toString() {
        return  id + ":  "
                + firstName + " "
                + middleName + " "
                + lastName + " "
                + phoneNum;
    }
}
