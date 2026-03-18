package com.clinique.controller;

import com.clinique.DAO.*;
import com.clinique.model.*;
import com.clinique.service.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class RendezVousController {
    @FXML private ComboBox<Patient> patientCombo;
    @FXML private ComboBox<Medecin> medecinCombo;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> heureCombo;
    @FXML private TextField motifField;
    @FXML private TableView<RendezVous> rdvTable;
    @FXML private TableColumn<RendezVous, String> patientCol;
    @FXML private TableColumn<RendezVous, String> medecinCol;
    @FXML private TableColumn<RendezVous, String> dateCol;
    @FXML private TableColumn<RendezVous, String> statutCol;
    @FXML private Label messageLabel;

    private final RendezVousService rdvService = new RendezVousService(new RendezVousDAO());
    private final PatientService patientService = new PatientService(new PatientDAO());
    private final MedecinService medecinService = new MedecinService(new MedecinDAO());

    @FXML
    public void initialize() {
        patientCombo.setItems(FXCollections.observableArrayList(patientService.listerTous()));
        medecinCombo.setItems(FXCollections.observableArrayList(medecinService.listerTous()));
        heureCombo.setItems(FXCollections.observableArrayList(
                "08:00","09:00","10:00","11:00","14:00","15:00","16:00","17:00"
        ));
        patientCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getPatient().toString()));
        medecinCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getMedecin().toString()));
        dateCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getDateHeure().toString()));
        statutCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getStatut().toString()));
        chargerRdv();

        System.out.println("Médecins : " + medecinService.listerTous().size());
        System.out.println("Patients : " + patientService.listerTous().size());
    }

    private void chargerRdv() {
        rdvTable.setItems(FXCollections.observableArrayList(rdvService.listerTous()));
    }

    @FXML
    public void ajouterRdv() {
        if (patientCombo.getValue() == null || medecinCombo.getValue() == null
                || datePicker.getValue() == null || heureCombo.getValue() == null) {
            messageLabel.setText("Remplissez tous les champs !");
            return;
        }
        LocalDateTime dateHeure = LocalDateTime.of(
                datePicker.getValue(),
                LocalTime.parse(heureCombo.getValue())
        );
        RendezVous r = new RendezVous();
        r.setPatient(patientCombo.getValue());
        r.setMedecin(medecinCombo.getValue());
        r.setDateHeure(dateHeure);
        r.setMotif(motifField.getText());
        boolean ok = rdvService.ajouter(r);
        messageLabel.setText(ok ? "Rendez-vous ajouté !" : "Conflit d'horaire !");
        chargerRdv();
    }

    @FXML
    public void filtrerParMedecin() {
        Medecin medecin = medecinCombo.getValue();
        if (medecin == null) {
            messageLabel.setText("Sélectionnez un médecin !");
            return;
        }
        rdvTable.setItems(FXCollections.observableArrayList(
                rdvService.listerParMedecin(medecin)));
        messageLabel.setText("RDV filtrés pour : " + medecin);
    }

    @FXML
    public void annulerRdv() {
        RendezVous r = rdvTable.getSelectionModel().getSelectedItem();
        if (r == null) { messageLabel.setText("Sélectionnez un RDV !"); return; }
        r.setStatut(StatutRendezVous.ANNULE);
        rdvService.modifier(r);
        messageLabel.setText("Rendez-vous annulé !");
        chargerRdv();
    }

    @FXML
    public void afficherRdvDuJour() {
        rdvTable.setItems(FXCollections.observableArrayList(rdvService.listerDuJour()));
    }
}