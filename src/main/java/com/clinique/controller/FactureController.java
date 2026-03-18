package com.clinique.controller;

import com.clinique.DAO.*;
import com.clinique.model.*;
import com.clinique.service.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.clinique.util.PdfGenerator;

public class FactureController {
    @FXML private ComboBox<Consultation> consultationCombo;
    @FXML private TextField montantField;
    @FXML private ComboBox<ModePaiement> modePaiementCombo;
    @FXML private ComboBox<StatutPaiement> statutPaiementCombo;
    @FXML private TableView<Facture> factureTable;
    @FXML private TableColumn<Facture, String> consultationCol;
    @FXML private TableColumn<Facture, String> montantCol;
    @FXML private TableColumn<Facture, String> modePaiementCol;
    @FXML private TableColumn<Facture, String> statutCol;
    @FXML private Label messageLabel;

    private final FactureService service = new FactureService(new FactureDAO());
    private final ConsultationService consultationService = new ConsultationService(new ConsultationDAO());
    private Facture factureSelectionnee = null;

    @FXML
    public void initialize() {
        consultationCombo.setItems(FXCollections.observableArrayList(consultationService.listerTous()));
        modePaiementCombo.setItems(FXCollections.observableArrayList(ModePaiement.values()));
        statutPaiementCombo.setItems(FXCollections.observableArrayList(StatutPaiement.values()));
        consultationCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(
                d.getValue().getConsultation().getPatient().toString()));
        montantCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(
                String.valueOf(d.getValue().getMontantTotal())));
        modePaiementCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(
                d.getValue().getModePaiement() != null ? d.getValue().getModePaiement().toString() : ""));
        statutCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(
                d.getValue().getStatutPaiement().toString()));
        chargerFactures();
        factureTable.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) factureSelectionnee = sel;
        });
    }

    private void chargerFactures() {
        factureTable.setItems(FXCollections.observableArrayList(service.listerTous()));
    }

    @FXML
    public void ajouterFacture() {
        if (consultationCombo.getValue() == null) {
            messageLabel.setText("Sélectionnez une consultation !");
            return;
        }
        Facture f = new Facture();
        f.setConsultation(consultationCombo.getValue());
        f.setMontantTotal(Double.parseDouble(montantField.getText()));
        f.setModePaiement(modePaiementCombo.getValue());
        f.setStatutPaiement(statutPaiementCombo.getValue() != null ?
                statutPaiementCombo.getValue() : StatutPaiement.NON_PAYE);
        service.ajouter(f);
        messageLabel.setText("Facture générée !");
        chargerFactures();
    }

    @FXML
    public void modifierFacture() {
        if (factureSelectionnee == null) {
            messageLabel.setText("Sélectionnez une facture !");
            return;
        }
        factureSelectionnee.setStatutPaiement(statutPaiementCombo.getValue());
        factureSelectionnee.setModePaiement(modePaiementCombo.getValue());
        service.modifier(factureSelectionnee);
        messageLabel.setText("Facture modifiée !");
        chargerFactures();
    }
    @FXML
    public void genererPdfFacture() {
        Facture f = factureTable.getSelectionModel().getSelectedItem();
        if (f == null) {
            messageLabel.setText("Sélectionnez une facture !");
            return;
        }
        String chemin = System.getProperty("user.home") + "/Desktop/facture_" + f.getId() + ".pdf";        PdfGenerator.genererFacture(f, chemin);
        messageLabel.setText("Facture PDF générée : " + chemin);
    }
}