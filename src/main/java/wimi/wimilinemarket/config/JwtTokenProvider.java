package wimi.wimilinemarket.config;

import wimi.wimilinemarket.entities.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // 🔐 Clave secreta (al menos 512 bits para HS256)
    private final String jwtSecret = "MiClaveSecretaSuperLargaYSeguraQueTieneAlMenos512BitsDeLongitud";

    // Tiempo de expiración (15 minutos en milisegundos)
    private final long jwtExpiration = 1000 * 60 * 15;

    // 🔐 Generar clave para firmar
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // ✅ Generar accessToken usando EMAIL y UUID como subject y añadiendo claims
    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getUsuarioId().toString()) // UUID del usuario como subject
                .claim("usuarioId", usuario.getUsuarioId().toString()) // UUID como claim adicional
                .claim("email", usuario.getEmail())            // Claim extra: email
                .claim("nombre", usuario.getNombre())          // opcional: nombre
                .claim("apellido", usuario.getApellido())      // opcional: apellido
                .setIssuedAt(new Date())                      // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Expiración
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Firmar con la clave secreta
                .compact();
    }

    // ✅ Validar si el token es válido y no expiró
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // Si no hay excepciones, el token es válido
            return true;
        } catch (Exception e) {
            return false; // Error → token inválido
        }
    }

    // ✅ Obtener ID del Usuario (UUID) del token
    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // Obtiene el subject (UUID del Usuario como string)
    }

    // ✅ Obtener email del token
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    // ✅ (Opcional) Obtener nombre del token
    public String getNombreFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("nombre", String.class);
    }

    // ✅ Obtener usuarioId del token
    public String getUsuarioIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("usuarioId", String.class); // Obtener el UUID desde el claim
    }
}
