package com.haulmont.testtask.ui.prescription;

import java.time.LocalDate;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.entity.Prescription;
import com.haulmont.testtask.entity.Priority;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class PrescriptionWindowAdd extends Window {
    private ListDataProvider<Prescription> dataProvider;
    private DaoServices<Prescription> prescriptionServices;
    private DaoServices<Doctor> doctorServices = new DoctorDao();
    private DaoServices<Patient> patientServices = new PatientDao();

    private TextArea description = new TextArea("Description");
    private ComboBox<Doctor> doctorComboBox = new ComboBox<>("Doctor");
    private ComboBox<Patient> patientComboBox = new ComboBox<>("Patient");
    private ComboBox<Priority> priorityComboBox = new ComboBox<>("Priority");
    private DateField createDate = new DateField("Date of creation");
    private DateField validityDate = new DateField("Validity");


    public PrescriptionWindowAdd(ListDataProvider<Prescription> dataProvider,
                                 DaoServices<Prescription> services) {
        this.dataProvider = dataProvider;
        this.prescriptionServices = services;
        setWidth("500px");
        setHeight("650px");
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

        description.setPlaceholder("Write here...");
        description.setSizeFull();
        mainLayout.addComponent(description);

        doctorComboBox.setItems(doctorServices.findAll());
        doctorComboBox.setItemCaptionGenerator(Doctor::toString);
        doctorComboBox.setSizeFull();
        mainLayout.addComponent(doctorComboBox);

        patientComboBox.setItems(patientServices.findAll());
        patientComboBox.setItemCaptionGenerator(Patient::toString);
        patientComboBox.setSizeFull();
        mainLayout.addComponent(patientComboBox);

        priorityComboBox.setItems(Priority.values());
        priorityComboBox.setSizeFull();
        mainLayout.addComponent(priorityComboBox);

        createDate.setSizeFull();
        mainLayout.addComponent(createDate);

        validityDate.setSizeFull();
        mainLayout.addComponent(validityDate);

        mainLayout.addComponents(addButtonWindow(), cancelButtonWindow());

        return mainLayout;
    }

    private Component addButtonWindow() {
        Button addButton = new Button("Ok");
        addButton.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        addButton.setWidthFull();

        // Validation of each form.
        Binder<Prescription> binder = new Binder<>();

        binder.forField(description)
                .withValidator(new BeanValidator(Prescription.class, "description"))
                .bind(Prescription::getDescription, Prescription::setDescription);

        binder.forField(doctorComboBox)
                .withValidator(new BeanValidator(Prescription.class, "doctor"))
                .bind(Prescription::getDoctor, Prescription::setDoctor);

        binder.forField(patientComboBox)
                .withValidator(new BeanValidator(Prescription.class, "patient"))
                .bind(Prescription::getPatient, Prescription::setPatient);

        binder.forField(priorityComboBox)
                .withValidator(new BeanValidator(Prescription.class, "priority"))
                .bind(Prescription::getPriority, Prescription::setPriority);

        binder.forField(createDate)
                .withValidator(new DateRangeValidator("Please choose another date",
                        LocalDate.now().minusYears(5), LocalDate.now()))
                .bind(Prescription::getCreateDate, Prescription::setCreateDate);

        binder.forField(validityDate)
                .withValidator(new DateRangeValidator("Please choose another date",
                        LocalDate.now(), LocalDate.now().plusYears(5)))
                .bind(Prescription::getValidity, Prescription::setValidity);

        // Сlick listener: if the data validation is successful -> add the data.
        addButton.addClickListener(event -> {
            if(binder.isValid()) {
                Prescription prescription = new Prescription(description.getValue(),
                        patientComboBox.getValue(), doctorComboBox.getValue(),
                        createDate.getValue(), validityDate.getValue(), priorityComboBox.getValue());
                prescriptionServices.save(prescription);
                dataProvider.getItems().add(prescription);
                dataProvider.refreshAll();
                close();
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
