package com.clinique.DAO;

import com.clinique.model.Medecin;
import javax.persistence.EntityManager;
import java.util.List;

public class MedecinDAO {
    public void ajouter(Medecin m, EntityManager em) { em.persist(m); }
    public void modifier(Medecin m, EntityManager em) { em.merge(m); }
    public void supprimer(Medecin m, EntityManager em) {
        em.remove(em.contains(m) ? m : em.merge(m));
    }
    public List<Medecin> listerTous(EntityManager em) {
        return em.createQuery("SELECT m FROM Medecin m", Medecin.class).getResultList();
    }
}
