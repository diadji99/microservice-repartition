package com.gestionHopital.serv_utilisateur.repository;

import com.gestionHopital.serv_utilisateur.modele.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
}
