package com.clinique.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity

public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime dateEmission = LocalDateTime.now();
    private double montantTotal;
    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;
    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement = StatutPaiement.NON_PAYE;
    @OneToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;
}
