package com.clinique.controller;

import com.clinique.DAO.PatientDAO;
import com.clinique.model.*;
import com.clinique.service.PatientService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class PatientController {
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private DatePicker dateNaissancePicker;
    @FXML private ComboBox<Sexe> sexeCombo;
    @FXML private TextField telephoneField;
    @FXML private TextField adresseField;
    @FXML private ComboBox<GroupeSanguin> groupeSanguinCombo;
    @FXML private TextArea antecedentsArea;
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, String> nomCol;
    @FXML private TableColumn<Patient, String> prenomCol;
    @FXML private TableColumn<Patient, String> telCol;
    @FXML private TextField rechercheField;
    @FXML private Label messageLabel;

    private final PatientService service = new PatientService(new PatientDAO());
    private Patient patientSelectionne = null;

    @FXML
    public void initialize() {
        sexeCombo.setItems(FXCollections.observableArrayList(Sexe.values()));
        groupeSanguinCombo.setItems(FXCollections.observableArrayList(GroupeSanguin.values()));

        nomCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNom()));
        prenomCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getPrenom()));
        telCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getTelephone()));

        chargerPatients();

        patientTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) remplirFormulaire(selected);
        });
    }

    private void chargerPatients() {
        List<Patient> patients = service.listerTous();
        patientTable.setItems(FXCollections.observableArrayList(patients));
    }

    private void remplirFormulaire(Patient p) {
        patientSelectionne = p;
        nomField.setText(p.getNom());
        prenomField.setText(p.getPrenom());
        dateNaissancePicker.setValue(p.getDateNaissance());
        sexeCombo.setValue(p.getSexe());
        telephoneField.setText(p.getTelephone());
        adresseField.setText(p.getAdresse());
        groupeSanguinCombo.setValue(p.getGroupeSanguin());
        antecedentsArea.setText(p.getAntecedentsMedicaux());
    }

    @FXML
    public void ajouterPatient() {
        Patient p = new Patient();
        p.setNom(nomField.getText());
        p.setPrenom(prenomField.getText());
        p.setDateNaissance(dateNaissancePicker.getValue());
        p.setSexe(sexeCombo.getValue());
        p.setTelephone(telephoneField.getText());
        p.setAdresse(adresseField.getText());
        p.setGroupeSanguin(groupeSanguinCombo.getValue());
        p.setAntecedentsMedicaux(antecedentsArea.getText());
        service.ajouter(p);
        messageLabel.setText("Patient ajouté !");
        viderFormulaire();
        chargerPatients();
    }

    @FXML
    public void modifierPatient() {
        if (patientSelectionne == null) {
            messageLabel.setText("Sélectionnez un patient !");
            return;
        }
        patientSelectionne.setNom(nomField.getText());
        patientSelectionne.setPrenom(prenomField.getText());
        patientSelectionne.setDateNaissance(dateNaissancePicker.getValue());
        patientSelectionne.setSexe(sexeCombo.getValue());
        patientSelectionne.setTelephone(telephoneField.getText());
        patientSelectionne.setAdresse(adresseField.getText());
        patientSelectionne.setGroupeSanguin(groupeSanguinCombo.getValue());
        patientSelectionne.setAntecedentsMedicaux(antecedentsArea.getText());
        service.modifier(patientSelectionne);
        messageLabel.setText("Patient modifié !");
        chargerPatients();
    }

    @FXML
    public void supprimerPatient() {
        if (patientSelectionne == null) {
            messageLabel.setText("Sélectionnez un patient !");
            return;
        }
        service.supprimer(patientSelectionne);
        messageLabel.setText("Patient supprimé !");
        viderFormulaire();
        chargerPatients();
    }

    @FXML
    public void rechercherPatient() {
        String mot = rechercheField.getText();
        patientTable.setItems(FXCollections.observableArrayList(service.rechercher(mot)));
    }

    private void viderFormulaire() {
        nomField.clear(); prenomField.clear();
        telephoneField.clear(); adresseField.clear();
        antecedentsArea.clear(); rechercheField.clear();
        dateNaissancePicker.setValue(null);
        sexeCombo.setValue(null);
        groupeSanguinCombo.setValue(null);
        patientSelectionne = null;
    }
    @FXML
    public void enregistrer() {
        ajouterPatient();
    }

    @FXML
    public void annuler() {
        viderFormulaire();
    }
}