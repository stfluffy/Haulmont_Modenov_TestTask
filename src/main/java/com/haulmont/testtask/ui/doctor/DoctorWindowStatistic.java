package com.haulmont.testtask.ui.doctor;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.entity.Doctor;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DoctorWindowStatistic extends Window {
    private Grid<Doctor> grid = new Grid<>();
    private DaoServices<Doctor> services = new DoctorDao();
    private ListDataProvider<Doctor> dataProvider = new ListDataProvider<>(services.findAll());

    public DoctorWindowStatistic() {
        setCaption("Statistic");
        setWidth("700px");
        setHeight("500px");
        setModal(true);
        setResizable(false);
        center();
        setContent(mainLayout());
    }

     private VerticalLayout mainLayout() {
         VerticalLayout layout = new VerticalLayout();
         layout.setSizeFull();
         layout.addComponent(grid);

         grid.setDataProvider(dataProvider);
         grid.setSizeFull();
         grid.addColumn(doctor -> doctor.getFirstName() + " " + doctor.getMiddleName()
                 + " " + doctor.getLastName()).setCaption("Doctor");
         grid.addColumn(Doctor::getSpecialization).setCaption("Specialization");
         grid.addColumn(doctor -> doctor.getPrescriptions().size()).setCaption("Prescriptions");
         return layout;
     }
}
