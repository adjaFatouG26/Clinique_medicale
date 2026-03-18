package com.clinique.controller;

import com.clinique.DAO.UtilisateurDAO;
import com.clinique.model.Utilisateur;
import com.clinique.service.UtilisateurService;
import com.clinique.util.SessionUtilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;

public class LoginController {
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private final UtilisateurService service =
            new UtilisateurService(new UtilisateurDAO());

    @FXML
    public void seConnecter() {
        String login = loginField.getText().trim();
        String mdp = passwordField.getText().trim();

        if (login.isEmpty() || mdp.isEmpty()) {
            messageLabel.setText("Veuillez remplir tous les champs !");
            return;
        }

        Utilisateur u = service.connecter(login, mdp);
        if (u != null) {
            SessionUtilisateur.ouvrir(u);
            try {
                URL fxmlUrl = getClass().getResource("/com/clinique/views/layout.fxml");
                System.out.println("URL layout : " + fxmlUrl);

                if (fxmlUrl == null) {
                    messageLabel.setText("Fichier layout.fxml introuvable !");
                    return;
                }

                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                Scene scene = new Scene(loader.load(), 900, 650);
                Stage stage = (Stage) loginField.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Clinique Médicale - " + u.getRole());
            } catch (Exception e) {
                e.printStackTrace();
                messageLabel.setText("Erreur : " + e.getMessage());
            }
        } else {
            messageLabel.setText("Login ou mot de passe incorrect !");
        }
    }
}