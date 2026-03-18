package com.clinique.util;

import com.clinique.DAO.MedecinDAO;
import com.clinique.DAO.UtilisateurDAO;
import com.clinique.config.FactoryJPA;
import com.clinique.model.*;
import org.mindrot.jbcrypt.BCrypt;
import javax.persistence.EntityManager;

public class InitData {
    public static void initialiser() {
        try {
            EntityManager em = FactoryJPA.getManager();
            UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
            MedecinDAO medecinDAO = new MedecinDAO();

            // Créer admin
            if (utilisateurDAO.trouverParLogin("admin", em) == null) {
                Utilisateur admin = new Utilisateur();
                admin.setLogin("admin");
                admin.setMotDePasseHash(BCrypt.hashpw("admin123", BCrypt.gensalt()));
                admin.setRole(Role.ADMIN);
                em.getTransaction().begin();
                utilisateurDAO.ajouter(admin, em);
                em.getTransaction().commit();
                System.out.println("✅ Admin créé !");
            }

            // Créer médecins de test
            if (medecinDAO.listerTous(em).isEmpty()) {
                em.getTransaction().begin();

                Medecin m1 = new Medecin();
                m1.setNom("Ndiaye");
                m1.setPrenom("Dr");
                m1.setSpecialite("Generaliste");
                m1.setTelephone("771117700");
                medecinDAO.ajouter(m1, em);

                Medecin m2 = new Medecin();
                m2.setNom("GUEYE");
                m2.setPrenom("Mr");
                m2.setSpecialite("Cardiologue");
                m2.setTelephone("777777777");
                medecinDAO.ajouter(m2, em);

                em.getTransaction().commit();
                System.out.println("✅ Médecins créés !");
            }

        } catch (Exception e) {
            System.out.println("❌ Erreur InitData : " + e.getMessage());
            e.printStackTrace();
        }
    }
}