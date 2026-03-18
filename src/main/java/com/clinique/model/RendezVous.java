package com.clinique.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity

public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime dateHeure;
    @Enumerated(EnumType.STRING)
    private StatutRendezVous statut = StatutRendezVous.PROGRAMME;
    private String motif;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Medecin medecin;
    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Patient patient;
}
