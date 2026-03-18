package com.clinique.DAO;

import com.clinique.model.Patient;
import javax.persistence.EntityManager;
import java.util.List;

public class PatientDAO {
    public void ajouter(Patient p, EntityManager em) { em.persist(p); }
    public void modifier(Patient p, EntityManager em) { em.merge(p); }
    public void supprimer(Patient p, EntityManager em) {
        em.remove(em.contains(p) ? p : em.merge(p));
    }
    public Patient trouverParId(int id, EntityManager em) {
        return em.find(Patient.class, id);
    }
    public List<Patient> listerTous(EntityManager em) {
        return em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }
    public List<Patient> rechercher(String mot, EntityManager em) {
        return em.createQuery(
                        "SELECT p FROM Patient p WHERE lower(p.nom) LIKE :m OR lower(p.prenom) LIKE :m",
                        Patient.class)
                .setParameter("m", "%" + mot.toLowerCase() + "%")
                .getResultList();
    }
}
