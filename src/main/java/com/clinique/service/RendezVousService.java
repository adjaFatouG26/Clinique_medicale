package com.clinique.service;

import com.clinique.DAO.RendezVousDAO;
import com.clinique.model.Medecin;
import com.clinique.model.RendezVous;
import com.clinique.config.FactoryJPA;
import javax.persistence.EntityManager;
import java.util.List;

public class RendezVousService {
    private final RendezVousDAO dao;
    public RendezVousService(RendezVousDAO dao) { this.dao = dao; }

    public List<RendezVous> listerParMedecin(Medecin medecin) {
        return dao.listerParMedecin(medecin, FactoryJPA.getManager());
    }

    public boolean ajouter(RendezVous r) {
        EntityManager em = FactoryJPA.getManager();
        if (dao.conflitHoraire(r.getMedecin(), r.getDateHeure(), em)) return false;
        em.getTransaction().begin();
        dao.ajouter(r, em);
        em.getTransaction().commit();
        return true;

    }
    public void modifier(RendezVous r) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        dao.modifier(r, em);
        em.getTransaction().commit();
    }
    public void supprimer(RendezVous r) {
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        dao.supprimer(r, em);
        em.getTransaction().commit();
    }
    public List<RendezVous> listerTous() {
        return dao.listerTous(FactoryJPA.getManager());
    }
    public List<RendezVous> listerDuJour() {
        return dao.listerDuJour(FactoryJPA.getManager());
    }
}
