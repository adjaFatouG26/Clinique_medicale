package com.clinique.service;

import com.clinique.DAO.PatientDAO;
import com.clinique.model.Patient;
import com.clinique.config.FactoryJPA;
import javax.persistence.EntityManager;
import java.util.List;

public class PatientService {
    private final PatientDAO dao;
    public PatientService(PatientDAO dao) { this.dao = dao; }

    public void ajouter(Patient p) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        dao.ajouter(p, em);
        em.getTransaction().commit();
    }
    public void modifier(Patient p) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        dao.modifier(p, em);
        em.getTransaction().commit();
    }
    public void supprimer(Patient p) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        dao.supprimer(p, em);
        em.getTransaction().commit();
    }
    public List<Patient> listerTous() {
        return dao.listerTous(FactoryJPA.getManager());
    }
    public List<Patient> rechercher(String mot) {
        return dao.rechercher(mot, FactoryJPA.getManager());
    }
}
