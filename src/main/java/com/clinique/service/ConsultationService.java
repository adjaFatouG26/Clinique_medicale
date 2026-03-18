package com.clinique.service;

import com.clinique.DAO.ConsultationDAO;
import com.clinique.model.Consultation;
import com.clinique.config.FactoryJPA;
import javax.persistence.EntityManager;
import java.util.List;

public class ConsultationService {
    private final ConsultationDAO dao;
    public ConsultationService(ConsultationDAO dao) { this.dao = dao; }

    public void ajouter(Consultation c) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        dao.ajouter(c, em);
        em.getTransaction().commit();
    }
    public void modifier(Consultation c) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        dao.modifier(c, em);
        em.getTransaction().commit();
    }
    public List<Consultation> listerTous() {
        return dao.listerTous(FactoryJPA.getManager());
    }
}
