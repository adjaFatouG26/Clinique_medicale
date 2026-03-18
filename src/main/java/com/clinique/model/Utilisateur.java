package com.clinique.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity

public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String motDePasseHash;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean actif = true;
}
