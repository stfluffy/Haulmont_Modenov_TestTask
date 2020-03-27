package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.entity.Patient;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class PatientWindowUpdate extends Window {
    private ListDataProvider<Patient> dataProvider;
    private DaoServices<Patient> services;
    private Patient patientUpdate;

    private TextField firstNameTextField = new TextField("First name");
    private TextField middleNameTextField = new TextField("Middle name");
    private TextField lastNameTextField = new TextField("Last name");
    private TextField phoneNumTextField = new TextField("Phone num");

    public PatientWindowUpdate(ListDataProvider<Patient> dataProvider,
                               DaoServices<Patient> services, Patient patient) {
        this.dataProvider = dataProvider;
        this.services = services;
        this.patientUpdate = patient;
        setCaption("Update");
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

        firstNameTextField.setPlaceholder("Elon");
        middleNameTextField.setPlaceholder("Reeve");
        lastNameTextField.setPlaceholder("Musk");
        phoneNumTextField.setPlaceholder("89991017050");

        mainLayout.addComponent(firstNameTextField);
        mainLayout.addComponent(middleNameTextField);
        mainLayout.addComponent(lastNameTextField);
        mainLayout.addComponent(phoneNumTextField);
        mainLayout.addComponents(updateButtonWindow(), cancelButtonWindow());

        return mainLayout;
    }

    private Component updateButtonWindow() {
        Button updateButton = new Button("Update");
        updateButton.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        updateButton.setWidthFull();

        // Validation of each form.
        Binder<Patient> binder = new Binder<>();

        binder.forField(firstNameTextField).withValidator(new BeanValidator(Patient.class, "firstName"))
                .bind(Patient::getFirstName, Patient::setFirstName);

        binder.forField(middleNameTextField).withValidator(new BeanValidator(Patient.class, "middleName"))
                .bind(Patient::getMiddleName, Patient::setMiddleName);

        binder.forField(lastNameTextField).withValidator(new BeanValidator(Patient.class, "lastName"))
                .bind(Patient::getLastName, Patient::setLastName);

        binder.forField(phoneNumTextField).withValidator(new BeanValidator(Patient.class, "phoneNum"))
                .bind(Patient::getPhoneNum, Patient::setPhoneNum);

        binder.readBean(patientUpdate);

        // Сlick listener: if the data validation is successful -> update the data.
        updateButton.addClickListener(event -> {
            if(binder.isValid()) {
                try {
                    binder.writeBean(patientUpdate);
                    services.update(patientUpdate);
                    dataProvider.refreshAll();
                    close();
                    Notification.show("Success!","", Notification.Type.HUMANIZED_MESSAGE);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
            } else {
                Notification.show("Warning: Check the date is correct!",
                        "", Notification.Type.WARNING_MESSAGE );
            }
        });
        return updateButton;
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
