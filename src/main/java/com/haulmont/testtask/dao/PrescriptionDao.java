package com.haulmont.testtask.dao;

import com.haulmont.testtask.dao.hibernate.until.HibernateSessionFactory;
import com.haulmont.testtask.entity.Prescription;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDao extends AbstractDao<Prescription> implements DaoServices<Prescription> {

    public Prescription getById(Long id) {
        Session session;
        Prescription prescription = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            prescription = session.load(Prescription.class, id);
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return prescription;
    }

    public List<Prescription> findAll() {
        Session session;
        List<Prescription> prescriptions = new ArrayList<>();
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            CriteriaQuery<Prescription> criteriaQuery = session.getCriteriaBuilder().createQuery(Prescription.class);
            criteriaQuery.from(Prescription.class);
            prescriptions = session.createQuery(criteriaQuery).getResultList();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return prescriptions;
    }
}
