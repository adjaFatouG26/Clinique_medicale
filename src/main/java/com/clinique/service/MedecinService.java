package com.clinique.service;

import com.clinique.DAO.MedecinDAO;
import com.clinique.model.Medecin;
import com.clinique.config.FactoryJPA;
import javax.persistence.EntityManager;
import java.util.List;

public class MedecinService {
    private final MedecinDAO dao;
    public MedecinService(MedecinDAO dao) { this.dao = dao; }

    public void ajouter(Medecin m) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        dao.ajouter(m, em);
        em.getTransaction().commit();
    }
    public void modifier(Medecin m) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        dao.modifier(m, em);
        em.getTransaction().commit();
    }
    public void supprimer(Medecin m) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        dao.supprimer(m, em);
        em.getTransaction().commit();
    }
    public List<Medecin> listerTous() {
        return dao.listerTous(FactoryJPA.getManager());
    }
}
