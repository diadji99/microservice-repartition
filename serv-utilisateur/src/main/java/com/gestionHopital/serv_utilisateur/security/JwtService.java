package com.gestionHopital.serv_utilisateur.security;

import com.gestionHopital.serv_utilisateur.modele.Utilisateur;
import com.gestionHopital.serv_utilisateur.service.UtilisateurService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static sun.net.www.protocol.http.AuthenticatorKeys.getKey;

@Service
public class JwtService {

    @Autowired
    private UtilisateurService utilisateurService;

    final long currentTime = System.currentTimeMillis();
    final long expirationTime = currentTime +30 * 60 * 1000;

    String ENCRYPTION_KEY = "78ea31ec5a1f7dece3de5e9bcc71b138238211623752d093fec3a67b421e2511";
    final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
    Key key = Keys.hmacShaKeyFor(decoder);

    public Map<String, String> generate(String username){
        Utilisateur utilisateur = (Utilisateur) this.utilisateurService.loadUserByUsername(username);
        return this.generateJwt(utilisateur);
    }

    private Map<String, String> generateJwt(Utilisateur utilisateur) {
        Map<String, Object> claims = Map.of(
                "nom", utilisateur.getNom(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, utilisateur.getEmail()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getEmail())
                .setClaims(claims)
                .signWith(key)
                .compact();
        return Map.of("bearer", bearer);
    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public Boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDataFromToken(token);
        assert expirationDate != null;
        return expirationDate.before(new Date());
    }

    private Date getExpirationDataFromToken(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
