package com.haulmont.testtask.ui.prescription;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.dao.PrescriptionDao;
import com.haulmont.testtask.entity.Prescription;
import com.haulmont.testtask.entity.Priority;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;

public class PrescriptionView extends VerticalLayout {
    private DaoServices<Prescription> services = new PrescriptionDao();
    private ListDataProvider<Prescription> dataProvider = new ListDataProvider<>(services.findAll());
    private Grid<Prescription> grid = new Grid<>(Prescription.class);

    public PrescriptionView() {
        setSizeFull();
        configurationHeader();

        addComponent(grid);
        updateGridList();
        configureGrid();
    }

    // Buttons layout.
    private void configurationHeader() {
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.addComponents(addButtonComponent(),
                updateButtonComponent(), deleteButtonComponent());
        addComponent(headerLayout);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("doctor", "patient", "priority", "description", "createDate", "validity");
        filterGridRow();
    }

    private void updateGridList() {
        grid.setDataProvider(dataProvider);
    }

    // Data filtering.
    private void filterGridRow() {
        HeaderRow headerRow = grid.appendHeaderRow();

        TextField doctorFilterField = new TextField();
        doctorFilterField.setSizeFull();
        doctorFilterField.setPlaceholder("Filter by doctor...");
        doctorFilterField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        headerRow.getCell("doctor").setComponent(doctorFilterField);

        doctorFilterField.addValueChangeListener(event ->
                dataProvider.addFilter(prescription -> StringUtils
                        .containsIgnoreCase(prescription.getDoctor().toString(),
                                doctorFilterField.getValue())));
        doctorFilterField.setValueChangeMode(ValueChangeMode.LAZY);

        TextField patientFilterField = new TextField();
        patientFilterField.setSizeFull();
        patientFilterField.setPlaceholder("Filter by patient...");
        patientFilterField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        headerRow.getCell("patient").setComponent(patientFilterField);

        patientFilterField.addValueChangeListener(event ->
                dataProvider.addFilter(prescription -> StringUtils
                        .containsIgnoreCase(prescription.getPatient().toString(),
                                patientFilterField.getValue())));
        patientFilterField.setValueChangeMode(ValueChangeMode.LAZY);

        ComboBox<Priority> priorityComboBox = new ComboBox<>();
        priorityComboBox.setItems(Priority.values());
        priorityComboBox.setSizeFull();
        priorityComboBox.setPlaceholder("Filter by priority...");
        priorityComboBox.setStyleName(ValoTheme.COMBOBOX_SMALL);
        headerRow.getCell("priority").setComponent(priorityComboBox);

        priorityComboBox.addValueChangeListener(event ->
            dataProvider.addFilter(prescription -> {
                if (priorityComboBox.getValue() != null) {
                   return StringUtils.containsIgnoreCase(prescription.getPriority().toString(),
                           priorityComboBox.getValue().toString());
                } else {
                    return true;
                }
            }));

        TextField descriptionFilterField = new TextField();
        descriptionFilterField.setSizeFull();
        descriptionFilterField.setPlaceholder("Filter by description...");
        descriptionFilterField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        headerRow.getCell("description").setComponent(descriptionFilterField);

        descriptionFilterField.addValueChangeListener(event ->
                    dataProvider.addFilter(prescription -> StringUtils
                            .containsIgnoreCase(prescription.getDescription(),
                                    descriptionFilterField.getValue())));
        descriptionFilterField.setValueChangeMode(ValueChangeMode.LAZY);
    }

    // Add button: opens the add window.
    private Component addButtonComponent() {
        Button button = new Button("Add");
        button.addStyleNames(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().addWindow(new PrescriptionWindowAdd(dataProvider, services)));
        return button;
    }

    // Update selected row button: check if the selected row is checked,
    // if so, opens the update window.
    private Component updateButtonComponent() {
        Button button = new Button("Update");
        button.addStyleNames(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);

        button.addClickListener(event -> {
            Prescription prescriptionSelected = null;

            for (Prescription prescription : grid.getSelectedItems()) {
                prescriptionSelected = prescription;
            }
            if(prescriptionSelected != null) {
                getUI().addWindow(new PrescriptionWindowUpdate(dataProvider, services, prescriptionSelected));
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
            Prescription prescriptionSelected = null;

            for (Prescription prescription : grid.getSelectedItems()) {
                prescriptionSelected = prescription;
            }
            if (prescriptionSelected != null) {
                try {
                    services.delete(prescriptionSelected);
                    dataProvider.getItems().remove(prescriptionSelected);
                    dataProvider.refreshAll();
                    Notification.show("Success!",
                            "", Notification.Type.HUMANIZED_MESSAGE);
                } catch (Exception exception) {
                    Notification.show("Error: " + exception, Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        return button;
    }
}
