package com.haulmont.testtask.dao;

import com.haulmont.testtask.dao.hibernate.until.HibernateSessionFactory;
import com.haulmont.testtask.entity.Doctor;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class DoctorDao extends AbstractDao<Doctor> implements DaoServices<Doctor> {

    public Doctor getById(Long id) {
        Session session;
        Doctor doctor = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            doctor = session.load(Doctor.class, id);
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return doctor;
    }

    public List<Doctor> findAll() {
        Session session;
        List<Doctor> doctors = new ArrayList<>();
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            CriteriaQuery<Doctor> criteriaQuery = session.getCriteriaBuilder().createQuery(Doctor.class);
            criteriaQuery.from(Doctor.class);
            doctors = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return doctors;
    }
}
