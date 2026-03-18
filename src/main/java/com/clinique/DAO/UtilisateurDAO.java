package com.clinique.DAO;

import com.clinique.model.Utilisateur;
import javax.persistence.EntityManager;
import java.util.List;

public class UtilisateurDAO {
    public void ajouter(Utilisateur u, EntityManager em) { em.persist(u); }
    public Utilisateur trouverParLogin(String login, EntityManager em) {
        return em.createQuery(
                        "SELECT u FROM Utilisateur u WHERE u.login = :login", Utilisateur.class)
                .setParameter("login", login)
                .getResultStream().findFirst().orElse(null);
    }
    public List<Utilisateur> listerTous(EntityManager em) {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }
}
