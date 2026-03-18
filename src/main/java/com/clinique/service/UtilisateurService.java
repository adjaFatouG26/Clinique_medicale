package com.clinique.service;

import com.clinique.DAO.UtilisateurDAO;
import com.clinique.model.Utilisateur;
import com.clinique.config.FactoryJPA;
import org.mindrot.jbcrypt.BCrypt;
import javax.persistence.EntityManager;

public class UtilisateurService {
    private final UtilisateurDAO dao;
    public UtilisateurService(UtilisateurDAO dao) { this.dao = dao; }

    public void ajouter(Utilisateur u) {
        u.setMotDePasseHash(BCrypt.hashpw(u.getMotDePasseHash(), BCrypt.gensalt()));
        EntityManager em = FactoryJPA.getManager();
        em.getTransaction().begin();
        dao.ajouter(u, em);
        em.getTransaction().commit();
    }

    public Utilisateur connecter(String login, String motDePasse) {
        EntityManager em = FactoryJPA.getManager();
        Utilisateur u = dao.trouverParLogin(login, em);
        if (u != null && BCrypt.checkpw(motDePasse, u.getMotDePasseHash()))
            return u;
        return null;
    }
}
