package com.gestionHopital.serv_utilisateur.controller;

import com.gestionHopital.serv_utilisateur.dto.AuthentificationDTO;
import com.gestionHopital.serv_utilisateur.modele.Administrateur;
import com.gestionHopital.serv_utilisateur.modele.Role;
import com.gestionHopital.serv_utilisateur.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.repository.UtilisateurRepository;
import com.gestionHopital.serv_utilisateur.security.JwtService;
import com.gestionHopital.serv_utilisateur.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping( "/api/utilisateur")
public class UtilisateurController {

    private static final Logger log = LoggerFactory.getLogger(UtilisateurController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/connexion")
    public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        final Authentication authenticat = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
        );

        if (authenticat.isAuthenticated()) {
            return this.jwtService.generate(authentificationDTO.username());
        }
        return  null;
    }

    @PostMapping(path = "/ajouterAdministrateur", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void ajouterAdministrateur(@RequestBody Administrateur administrateur) {

        if (!administrateur.getEmail().contains("@")){
            throw new RuntimeException("Email invalide");
        }

        if (!administrateur.getEmail().contains(".")){
            throw new RuntimeException("Email invalide");
        }

        Optional<Utilisateur> administrateurOptional = utilisateurRepository.findByEmail(administrateur.getEmail());
        if (administrateurOptional.isPresent()){
            throw new RuntimeException("Email est déjà utiliser");
        }

        String mdpCryp = this.passwordEncoder.encode(administrateur.getPassword());
        administrateur.setPassword(mdpCryp);
        this.utilisateurService.ajouterAdministrateur(administrateur);
    }

    @PostMapping(path = "/ajouterRole")
    public void ajouterRole(@RequestBody Role role) {
        this.utilisateurService.ajouterRole(role);
    }
}
