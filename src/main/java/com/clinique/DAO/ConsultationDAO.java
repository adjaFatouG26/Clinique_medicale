package com.clinique.DAO;

import com.clinique.model.Consultation;
import javax.persistence.EntityManager;
import java.util.List;

public class ConsultationDAO {
    public void ajouter(Consultation c, EntityManager em) { em.persist(c); }
    public void modifier(Consultation c, EntityManager em) { em.merge(c); }
    public List<Consultation> listerTous(EntityManager em) {
        return em.createQuery("SELECT c FROM Consultation c", Consultation.class).getResultList();
    }
}
