package com.gestionHopital.serv_utilisateur.service;

import com.gestionHopital.serv_utilisateur.modele.*;
import com.gestionHopital.serv_utilisateur.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UtilisateurService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private AdministrateurRepository administrateurRepository;
    @Autowired
    private MedecinRepository MedecinRepository;
    @Autowired
    private InfirmierRepository infirmierRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Role ajouterRole(Role role) {
        roleRepository.save(role);
        return role;
    }

    public void ajouterAdministrateur(Administrateur administrateur) {
        // Récupérer le rôle "ADMINISTRATEUR"
        Role roleAdministrateur = roleRepository.findByLibelle("ADMINISTRATEUR")
                .orElseThrow(() -> new RuntimeException("Le rôle ADMINISTRATEUR n'existe pas"));
        // Associer le rôle à l'administrateur
        administrateur.setRole(roleAdministrateur);
        // Sauvegarder l'administrateur
        administrateurRepository.save(administrateur);
    }

    public void ajouterMedecin(Medecin medecin) {
        this.MedecinRepository.save(medecin);
    }

    public void ajouterInfirmier(Infirmier infirmier) {
        this.infirmierRepository.save(infirmier);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec cet email : " + email));
    }
}
