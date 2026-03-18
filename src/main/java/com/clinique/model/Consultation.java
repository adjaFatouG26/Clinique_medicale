package com.clinique.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity

public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date = LocalDateTime.now();
    @Column(columnDefinition = "TEXT")
    private String diagnostic;
    @Column(columnDefinition = "TEXT")
    private String observations;
    @Column(columnDefinition = "TEXT")
    private String prescription;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;
    @OneToOne
    @JoinColumn(name = "rendez_vous_id")
    private RendezVous rendezVous;
}
