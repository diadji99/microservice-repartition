package com.gestionHopital.serv_utilisateur.repository;

import com.gestionHopital.serv_utilisateur.modele.Infirmier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfirmierRepository extends JpaRepository<Infirmier,Long> {
}
