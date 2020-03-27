package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.entity.Doctor;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class DoctorWindowAdd extends Window {
    private ListDataProvider<Doctor> dataProvider;
    private DaoServices<Doctor> services;

    private TextField firstNameTextField = new TextField("First name");
    private TextField middleNameTextField = new TextField("Middle name");
    private TextField lastNameTextField = new TextField("Last name");
    private TextField specializationTextField = new TextField("Specialization");

    public DoctorWindowAdd(ListDataProvider<Doctor> dataProvider, DaoServices<Doctor> services) {
        this.dataProvider = dataProvider;
        this.services = services;
        setCaption("Add");
        setModal(true);
        setResizable(false);
        center();
        setContent(mainLayoutContent());
    }

    // Creates the main layout of the window.
    // Configures and adds components to the layout.
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
        mainLayout.addComponents(addButtonWindow(), cancelButtonWindow());

        return mainLayout;
    }

    private Component addButtonWindow() {
        Button addButton = new Button("Ok");
        addButton.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        addButton.setWidthFull();

        // Validation of each form.
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

        // Сlick listener: if the data validation is successful -> add the data.
        addButton.addClickListener(event -> {
            if (binder.isValid()) {
                Doctor addDoctor = new Doctor(firstNameTextField.getValue(), middleNameTextField.getValue(),
                        lastNameTextField.getValue(), specializationTextField.getValue());
                services.save(addDoctor);
                dataProvider.getItems().add(addDoctor);
                dataProvider.refreshAll();
                close();
                Notification.show("Success!","", Notification.Type.HUMANIZED_MESSAGE);
            } else {
                Notification.show("Warning: Check the date is correct!",
                        "", Notification.Type.WARNING_MESSAGE);
            }
        });
        return addButton;
    }

    // Button to cancel and close the update window.
    private Component cancelButtonWindow() {
        Button cancelButton = new Button("Cancel");
        cancelButton.addStyleNames(ValoTheme.BUTTON_DANGER);
        cancelButton.setWidthFull();

        cancelButton.addClickListener(event -> close());
        return cancelButton;
    }
}
