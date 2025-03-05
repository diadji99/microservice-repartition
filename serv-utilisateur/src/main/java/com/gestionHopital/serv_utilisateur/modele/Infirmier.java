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
public class Infirmier extends Utilisateur{
    @Column(unique=true)
    private String NumeroProfessionnel;
    private String specialite;
}
