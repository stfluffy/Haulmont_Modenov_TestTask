package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.entity.Doctor;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;

public class DoctorView extends VerticalLayout {
    private Grid<Doctor> grid = new Grid<>(Doctor.class);
    private DaoServices<Doctor> services = new DoctorDao();
    private ListDataProvider<Doctor> dataProvider = new ListDataProvider<>(services.findAll());

    public DoctorView() {
        setSizeFull();
        configurationHeader();

        addComponent(grid);
        updateGridList();
        configureGrid();
    }

    // Buttons layout.
    private void configurationHeader() {
        HorizontalLayout headerLayout = new HorizontalLayout();

        headerLayout.addComponents(searchComponent(), addButtonComponent(),
                updateButtonComponent(), deleteButtonComponent(), statisticButtonComponent());
        addComponent(headerLayout);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("firstName", "middleName", "lastName", "specialization");
    }

    private void updateGridList() {
        grid.setDataProvider(dataProvider);
    }

    // Data filtering.
    private Component searchComponent() {
        TextField textField = new TextField();
        textField.setPlaceholder("Search by name...");
        textField.addStyleName(ValoTheme.TEXTFIELD_SMALL);

        textField.addValueChangeListener(event ->
            dataProvider.addFilter(doctor ->
                    StringUtils.containsIgnoreCase(doctor.getFirstName(), textField.getValue())));
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        return textField;
    }

    // Add button: opens the add window.
    private Component addButtonComponent() {
        Button button = new Button("Add");
        button.addStyleNames(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);

        button.addClickListener(event ->
                getUI().addWindow(new DoctorWindowAdd(dataProvider, services)));
        return button;
    }

    // Update selected row button: check if the selected row is checked,
    // if so, opens the update window.
    private Component updateButtonComponent() {
        Button button = new Button("Update");
        button.addStyleNames(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);

        button.addClickListener(event -> {
            Doctor doctor = null;
            for (Doctor doctorSelected : grid.getSelectedItems()) {
                doctor = doctorSelected;
            }
            if (doctor != null) {
                getUI().addWindow(new DoctorWindowUpdate(dataProvider, services, doctor));
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
            Doctor doctorSelected = null;
            for (Doctor doctor : grid.getSelectedItems()) {
                doctorSelected = doctor;
            }
            if (doctorSelected != null) {
                try {
                    if (services.getById(doctorSelected.getId()).getPrescriptions().isEmpty()) {
                        services.delete(doctorSelected);
                        dataProvider.getItems().remove(doctorSelected);
                        dataProvider.refreshAll();
                        Notification.show("Success!",
                                "", Notification.Type.HUMANIZED_MESSAGE);
                    } else {
                        Notification.show("Warning: Can't remove a doctor with a relevant prescription!",
                                "", Notification.Type.WARNING_MESSAGE);
                    }
                } catch (Exception e) {
                    Notification.show("Error: " + e, Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        return button;
    }

    // Statistic button: opens the statistic window.
    private Component statisticButtonComponent() {
        Button button = new Button("Statistic");
        button.addStyleNames(ValoTheme.BUTTON_FRIENDLY, ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().addWindow(new DoctorWindowStatistic()));
        return button;
    }
}
