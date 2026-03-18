package com.clinique.controller;

import com.clinique.util.Navigation;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import com.clinique.DAO.ConsultationDAO;
import com.clinique.DAO.RendezVousDAO;
import com.clinique.service.ConsultationService;
import com.clinique.service.RendezVousService;
import com.clinique.util.PdfGenerator;

public class LayoutController {
    @FXML private StackPane stackPane;

    @FXML
    public void afficherPatients() {
        Navigation.loadView("patients.fxml", stackPane);
    }
    @FXML
    public void afficherRendezVous() {
        Navigation.loadView("rendezVous.fxml", stackPane);
    }
    @FXML
    public void afficherConsultations() {
        Navigation.loadView("consultations.fxml", stackPane);
    }
    @FXML
    public void afficherFactures() {
        Navigation.loadView("factures.fxml", stackPane);
    }
    @FXML
    public void genererRapportJournalier() {
        RendezVousService rdvService = new RendezVousService(new RendezVousDAO());
        ConsultationService consultService = new ConsultationService(new ConsultationDAO());
        String chemin = System.getProperty("user.home") + "/Desktop/rapport_journalier_"
                + java.time.LocalDate.now() + ".pdf";
        PdfGenerator.genererRapportJournalier(
                rdvService.listerDuJour(),
                consultService.listerTous(),
                chemin
        );
        System.out.println("✅ Rapport généré : " + chemin);
    }
}