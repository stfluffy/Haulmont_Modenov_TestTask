package com.haulmont.testtask.ui.patient;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.entity.Patient;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;

public class PatientView extends VerticalLayout {
    private Grid<Patient> grid = new Grid<>(Patient.class);
    private DaoServices<Patient> services = new PatientDao();
    private ListDataProvider<Patient> dataProvider = new ListDataProvider<>(services.findAll());

    public PatientView() {
        setSizeFull();
        configurationHeader();

        addComponent(grid);
        updateGridList();
        configureGrid();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("firstName", "middleName", "lastName", "phoneNum");
    }

    private void updateGridList() {
        grid.setDataProvider(dataProvider);
    }

    // Buttons layout.
    private void configurationHeader() {
        HorizontalLayout headerLayout = new HorizontalLayout();

        headerLayout.addComponents(searchComponent(), addButtonComponent(),
                updateButtonComponent(), deleteButtonComponent());

        addComponent(headerLayout);
    }

    // Data filtering.
    private Component searchComponent() {
        TextField textField = new TextField();
        textField.setPlaceholder("Search by name...");
        textField.addStyleName(ValoTheme.TEXTFIELD_SMALL);

        textField.addValueChangeListener(event ->
                    dataProvider.addFilter(patient ->
                            StringUtils.containsIgnoreCase(patient.getFirstName(), textField.getValue())));
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        return textField;
    }

    // Add button: opens the add window.
    private Component addButtonComponent() {
        Button button = new Button("Add");
        button.addStyleNames(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().addWindow(new PatientWindowAdd(dataProvider, services)));
        return button;
    }

    // Update selected row button: check if the selected row is checked,
    // if so, opens the update window.
    private Component updateButtonComponent() {
        Button button = new Button("Update");
        button.addStyleNames(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);

        button.addClickListener(clickEvent -> {
            Patient patient = null;
            for (Patient patientSelected : grid.getSelectedItems()) {
                patient = patientSelected;
            }
            if (patient != null) {
                getUI().addWindow(new PatientWindowUpdate(dataProvider, services, patient));
            } else {
                Notification.show("Warning: You did not select any row!",
                        "", Notification.Type.WARNING_MESSAGE );
            }
        });
        return button;
    }

    // Delete selected row button: check if the selected row is checked,
    // if so, delete the data and updates the table.
    private Component deleteButtonComponent() {
        Button button = new Button("Delete");
        button.addStyleNames(ValoTheme.BUTTON_DANGER, ValoTheme.BUTTON_SMALL);

        button.addClickListener(event -> {
            Patient patientSelected = null;
            for (Patient patient : grid.getSelectedItems()) {
                patientSelected = patient;
            }
            if (patientSelected != null) {
                try {
                    if (services.getById(patientSelected.getId()).getPrescriptions().isEmpty()) {
                        services.delete(patientSelected);
                        dataProvider.getItems().remove(patientSelected);
                        dataProvider.refreshAll();
                        Notification.show("Success!",
                                "", Notification.Type.HUMANIZED_MESSAGE);
                    } else Notification.show("Warning: Can't remove a patient with a relevant prescription!",
                            "", Notification.Type.WARNING_MESSAGE);
                } catch (Exception exception) {
                    Notification.show("Error: " + exception, Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        return button;
    }
}
