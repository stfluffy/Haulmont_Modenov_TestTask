package com.haulmont.testtask.dao;

import com.haulmont.testtask.dao.hibernate.until.HibernateSessionFactory;
import com.haulmont.testtask.entity.Patient;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class PatientDao extends AbstractDao<Patient> implements DaoServices<Patient> {

    public Patient getById(Long id) {
        Session session;
        Patient patient = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            patient = session.load(Patient.class, id);
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return patient;
    }

    public List<Patient> findAll() {
        Session session;
        List<Patient> patient = new ArrayList<>();
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            CriteriaQuery<Patient> criteriaQuery = session.getCriteriaBuilder().createQuery(Patient.class);
            criteriaQuery.from(Patient.class);
            patient = session.createQuery(criteriaQuery).getResultList();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return patient;
    }
}
