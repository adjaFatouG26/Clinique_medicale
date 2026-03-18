package com.clinique.controller;

import com.clinique.DAO.*;
import com.clinique.model.*;
import com.clinique.service.*;
import com.clinique.util.PdfGenerator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class ConsultationController {
    @FXML private ComboBox<Patient> patientCombo;
    @FXML private ComboBox<Medecin> medecinCombo;
    @FXML private ComboBox<RendezVous> rdvCombo;
    @FXML private TextArea diagnosticArea;
    @FXML private TextArea observationsArea;
    @FXML private TextArea prescriptionArea;
    @FXML private TableView<Consultation> consultationTable;
    @FXML private TableColumn<Consultation, String> patientCol;
    @FXML private TableColumn<Consultation, String> medecinCol;
    @FXML private TableColumn<Consultation, String> dateCol;
    @FXML private TableColumn<Consultation, String> diagnosticCol;
    @FXML private Label messageLabel;

    private final ConsultationService service = new ConsultationService(new ConsultationDAO());
    private final PatientService patientService = new PatientService(new PatientDAO());
    private final MedecinService medecinService = new MedecinService(new MedecinDAO());
    private final RendezVousService rdvService = new RendezVousService(new RendezVousDAO());

    @FXML
    public void initialize() {
        patientCombo.setItems(FXCollections.observableArrayList(patientService.listerTous()));
        medecinCombo.setItems(FXCollections.observableArrayList(medecinService.listerTous()));
        rdvCombo.setItems(FXCollections.observableArrayList(rdvService.listerTous()));
        patientCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getPatient().toString()));
        medecinCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getMedecin().toString()));
        dateCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getDate().toString()));
        diagnosticCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getDiagnostic()));
        chargerConsultations();
    }

    private void chargerConsultations() {
        consultationTable.setItems(FXCollections.observableArrayList(service.listerTous()));
    }

    @FXML
    public void ajouterConsultation() {
        if (patientCombo.getValue() == null || medecinCombo.getValue() == null) {
            messageLabel.setText("Sélectionnez patient et médecin !");
            return;
        }
        Consultation c = new Consultation();
        c.setPatient(patientCombo.getValue());
        c.setMedecin(medecinCombo.getValue());
        c.setRendezVous(rdvCombo.getValue());
        c.setDiagnostic(diagnosticArea.getText());
        c.setObservations(observationsArea.getText());
        c.setPrescription(prescriptionArea.getText());
        service.ajouter(c);
        messageLabel.setText("Consultation enregistrée !");
        chargerConsultations();
    }
    @FXML
    public void genererOrdonnance() {
        Consultation c = consultationTable.getSelectionModel().getSelectedItem();
        if (c == null) {
            messageLabel.setText("Sélectionnez une consultation !");
            return;
        }
        String chemin = System.getProperty("user.home") + "/Desktop/ordonnance_" + c.getId() + ".pdf";        PdfGenerator.genererOrdonnance(c, chemin);
        messageLabel.setText("Ordonnance générée : " + chemin);
    }
}