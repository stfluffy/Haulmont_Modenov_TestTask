package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.entity.Doctor;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class DoctorWindowUpdate extends Window {
    private ListDataProvider<Doctor> dataProvider;
    private DaoServices<Doctor> services;
    private Doctor doctorUpdate;

    private TextField firstNameTextField = new TextField("First name");
    private TextField middleNameTextField = new TextField("Middle name");
    private TextField lastNameTextField = new TextField("Last name");
    private TextField specializationTextField = new TextField("Specialization");

    public DoctorWindowUpdate(ListDataProvider<Doctor> dataProvider,
                              DaoServices<Doctor> services, Doctor doctor) {
        this.dataProvider = dataProvider;
        this.services = services;
        this.doctorUpdate = doctor;
        setCaption("Update");
        setModal(true);
        setResizable(false);
        center();
        setContent(mainLayoutContent());
    }

    private VerticalLayout mainLayoutContent() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidthFull();

        firstNameTextField.setPlaceholder("Nikolai");
        middleNameTextField.setPlaceholder("Vasilyevich");
        lastNameTextField.setPlaceholder("Sklifosovsky");
        specializationTextField.setPlaceholder("Surgeon");

        mainLayout.addComponent(firstNameTextField);
        mainLayout.addComponent(middleNameTextField);
        mainLayout.addComponent(lastNameTextField);
        mainLayout.addComponent(specializationTextField);
        mainLayout.addComponents(updateButtonWindow(), cancelButtonWindow());

        return mainLayout;
    }

    private Component updateButtonWindow() {
        Button updateButton = new Button("Update");
        updateButton.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        updateButton.setWidthFull();

        Binder<Doctor> binder = new Binder<>();

        binder.forField(firstNameTextField)
                .withValidator(new BeanValidator(Doctor.class, "firstName"))
                .bind(Doctor::getFirstName, Doctor::setFirstName);

        binder.forField(middleNameTextField)
                .withValidator(new BeanValidator(Doctor.class, "middleName"))
                .bind(Doctor::getMiddleName, Doctor::setMiddleName);

        binder.forField(lastNameTextField)
                .withValidator(new BeanValidator(Doctor.class, "lastName"))
                .bind(Doctor::getLastName, Doctor::setLastName);
        binder.forField(specializationTextField)
                .withValidator(new BeanValidator(Doctor.class, "specialization"))
                .bind(Doctor::getSpecialization, Doctor::setSpecialization);

        binder.readBean(doctorUpdate);

        updateButton.addClickListener(event -> {
            if (binder.isValid()) {
                try {
                    binder.writeBean(doctorUpdate);
                    services.update(doctorUpdate);
                    dataProvider.refreshAll();
                    close();
                    Notification.show("Success!","", Notification.Type.HUMANIZED_MESSAGE);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
            } else Notification.show("Warning: Check the date is correct!",
                    "", Notification.Type.WARNING_MESSAGE );
        });
        return updateButton;
    }

    private Component cancelButtonWindow() {
        Button cancelButton = new Button("Cancel");
        cancelButton.addStyleNames(ValoTheme.BUTTON_DANGER);
        cancelButton.setWidthFull();

        cancelButton.addClickListener(event -> close());
        return cancelButton;
    }
}
