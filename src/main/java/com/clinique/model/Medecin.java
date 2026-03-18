package com.clinique.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity

public class Medecin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String specialite;
    private String telephone;

    @Override
    public String toString() { return "Dr. " + prenom + " " + nom; }
}
