package com.gestionHopital.serv_utilisateur.repository;

import com.gestionHopital.serv_utilisateur.modele.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {
}
