package com.haulmont.testtask.ui.main;

import com.haulmont.testtask.ui.doctor.DoctorView;
import com.haulmont.testtask.ui.patient.PatientView;
import com.haulmont.testtask.ui.prescription.PrescriptionView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
@Title("Modenov test-task")
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        setContent(mainLayout);

        TabSheet tabsheet = new TabSheet();
        mainLayout.addComponent(tabsheet);
        mainLayout.setComponentAlignment(tabsheet, Alignment.BOTTOM_CENTER);

        tabsheet.addTab(mainTab(), "Home");
        tabsheet.addTab(new PatientView(), "Patient");
        tabsheet.addTab(new DoctorView(), "Doctor");
        tabsheet.addTab(new PrescriptionView(), "Prescription");
        tabsheet.setSizeFull();

        Label copyrightLabel = new Label("Copyrights © 2020 Modenov D.A. All rights NOT reserved!");
        copyrightLabel.addStyleNames(ValoTheme.LABEL_SMALL);
        mainLayout.addComponent(copyrightLabel);
        mainLayout.setComponentAlignment(copyrightLabel, Alignment.BOTTOM_CENTER);
    }

    private VerticalLayout mainTab () {
        VerticalLayout mainLayout = new VerticalLayout();

        Label welcomeLabel = new Label("HAULMONT test-task");
        welcomeLabel.addStyleNames(ValoTheme.LABEL_H1, ValoTheme.LABEL_COLORED);
        mainLayout.addComponent(welcomeLabel);
        mainLayout.setComponentAlignment(welcomeLabel, Alignment.MIDDLE_CENTER);

        Label continueLabel = new Label("Select tab to continue)");
        continueLabel.addStyleNames(ValoTheme.LABEL_H2);
        mainLayout.addComponent(continueLabel);
        mainLayout.setComponentAlignment(continueLabel, Alignment.MIDDLE_CENTER);

        return mainLayout;
    }
}
