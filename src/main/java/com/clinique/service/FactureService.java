package com.clinique.service;
import com.clinique.DAO.FactureDAO;
import com.clinique.model.Facture;
import com.clinique.config.FactoryJPA;
import javax.persistence.EntityManager;
import java.util.List;

public class FactureService {
    private final FactureDAO dao;
    public FactureService(FactureDAO dao) { this.dao = dao; }

    public void ajouter(Facture f) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        dao.ajouter(f, em);
        em.getTransaction().commit();
    }
    public void modifier(Facture f) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        dao.modifier(f, em);
        em.getTransaction().commit();
    }
    public List<Facture> listerTous() {
        return dao.listerTous(FactoryJPA.getManager());
    }
}
