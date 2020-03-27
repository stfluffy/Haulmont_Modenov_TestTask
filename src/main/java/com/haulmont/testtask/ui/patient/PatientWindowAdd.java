package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.entity.Patient;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class PatientWindowAdd extends Window {
    private ListDataProvider<Patient> dataProvider;
    private DaoServices<Patient> services;

    private TextField firstNameTextField = new TextField("First name");
    private TextField middleNameTextField = new TextField("Middle name");
    private TextField lastNameTextField = new TextField("Last name");
    private TextField phoneNumTextField = new TextField("Phone num");

    public PatientWindowAdd(ListDataProvider<Patient> dataProvider, DaoServices<Patient> services) {
        this.dataProvider = dataProvider;
        this.services = services;
        setCaption("Add");
        setModal(true);
        setResizable(false);
        center();
        setContent(mainLayoutContent());
    }

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
        mainLayout.addComponents(addButtonWindow(), cancelButtonWindow());

        return mainLayout;
    }

    private Component addButtonWindow() {
        Button addButton = new Button("Ok");
        addButton.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        addButton.setWidthFull();

        Binder<Patient> binder = new Binder<>();
        binder.forField(firstNameTextField)
                .withValidator(new BeanValidator(Patient.class, "firstName"))
                .bind(Patient::getFirstName, Patient::setFirstName);

        binder.forField(middleNameTextField)
                .withValidator(new BeanValidator(Patient.class, "middleName"))
                .bind(Patient::getMiddleName, Patient::setMiddleName);

        binder.forField(lastNameTextField)
                .withValidator(new BeanValidator(Patient.class, "lastName"))
                .bind(Patient::getLastName, Patient::setLastName);

        binder.forField(phoneNumTextField)
                .withValidator(new BeanValidator(Patient.class, "phoneNum"))
                .bind(Patient::getPhoneNum, Patient::setPhoneNum);

        addButton.addClickListener(event -> {
            if(binder.isValid()) {
                Patient addPatient = new Patient(firstNameTextField.getValue(), middleNameTextField.getValue(),
                        lastNameTextField.getValue(), phoneNumTextField.getValue());
                services.save(addPatient);
                dataProvider.getItems().add(addPatient);
                dataProvider.refreshAll();
                close();
                Notification.show("Success!","", Notification.Type.HUMANIZED_MESSAGE);
            } else Notification.show("Warning: Check the date is correct!",
                    "", Notification.Type.WARNING_MESSAGE );
        });
        return addButton;
    }

    private Component cancelButtonWindow() {
        Button cancelButton = new Button("Cancel");
        cancelButton.addStyleNames(ValoTheme.BUTTON_DANGER);
        cancelButton.setWidthFull();

        cancelButton.addClickListener(event -> close());
        return cancelButton;
    }
}
