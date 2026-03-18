package com.clinique.DAO;

import com.clinique.model.Medecin;
import com.clinique.model.RendezVous;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RendezVousDAO {
    public void ajouter(RendezVous r, EntityManager em) { em.persist(r); }
    public void modifier(RendezVous r, EntityManager em) { em.merge(r); }
    public void supprimer(RendezVous r, EntityManager em) {
        em.remove(em.contains(r) ? r : em.merge(r));
    }
    public List<RendezVous> listerTous(EntityManager em) {
        return em.createQuery("SELECT r FROM RendezVous r", RendezVous.class).getResultList();
    }
    public List<RendezVous> listerDuJour(EntityManager em) {
        LocalDateTime debut = LocalDate.now().atStartOfDay();
        LocalDateTime fin = debut.plusDays(1);
        return em.createQuery(
                        "SELECT r FROM RendezVous r WHERE r.dateHeure >= :d AND r.dateHeure < :f",
                        RendezVous.class)
                .setParameter("d", debut)
                .setParameter("f", fin)
                .getResultList();
    }

    public List<RendezVous> listerParMedecin(Medecin medecin, EntityManager em) {
        return em.createQuery(
                        "SELECT r FROM RendezVous r WHERE r.medecin = :m", RendezVous.class)
                .setParameter("m", medecin)
                .getResultList();
    }
    public boolean conflitHoraire(Medecin medecin, LocalDateTime dateHeure, EntityManager em) {
        Long count = em.createQuery(
                        "SELECT COUNT(r) FROM RendezVous r WHERE r.medecin = :m AND r.dateHeure = :d",
                        Long.class)
                .setParameter("m", medecin)
                .setParameter("d", dateHeure)
                .getSingleResult();
        return count > 0;
    }
}
