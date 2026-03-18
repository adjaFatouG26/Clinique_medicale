package com.clinique.util;

import com.clinique.model.Utilisateur;

public class SessionUtilisateur {
    private static Utilisateur utilisateurCourant;

    public static void ouvrir(Utilisateur u) { utilisateurCourant = u; }
    public static void fermer() { utilisateurCourant = null; }
    public static Utilisateur get() { return utilisateurCourant; }
    public static boolean estConnecte() { return utilisateurCourant != null; }
}
