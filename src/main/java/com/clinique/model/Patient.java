package com.clinique.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    private String telephone;
    private String adresse;
    @Enumerated(EnumType.STRING)
    private GroupeSanguin groupeSanguin;
    @Column(columnDefinition = "TEXT")
    private String antecedentsMedicaux;

    @Override
    public String toString() { return prenom + " " + nom; }
}
