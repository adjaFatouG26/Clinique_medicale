package com.clinique.DAO;

import com.clinique.model.Facture;
import javax.persistence.EntityManager;
import java.util.List;

public class FactureDAO {
    public void ajouter(Facture f, EntityManager em) { em.persist(f); }
    public void modifier(Facture f, EntityManager em) { em.merge(f); }
    public List<Facture> listerTous(EntityManager em) {
        return em.createQuery("SELECT f FROM Facture f", Facture.class).getResultList();
    }
}