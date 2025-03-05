package com.gestionHopital.serv_utilisateur.modele;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Administrateur extends Utilisateur{
    @Column(unique=true)
    private String NumeroProfessionnel;

    public Administrateur(String nom, String prenom, String email, String password, String numeroProfessionnel) {
        super();
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setEmail(email);
        this.setPassword(password);
        this.NumeroProfessionnel = numeroProfessionnel;
    }

}
