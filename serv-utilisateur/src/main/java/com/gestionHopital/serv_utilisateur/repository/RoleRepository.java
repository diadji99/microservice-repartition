package com.gestionHopital.serv_utilisateur.repository;

import com.gestionHopital.serv_utilisateur.modele.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
        Optional<Role> findByLibelle(String libelle);
}
