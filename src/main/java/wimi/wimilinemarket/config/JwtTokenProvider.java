package wimi.wimilinemarket.config;

import wimi.wimilinemarket.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // ⚠️ Asegúrate de que esta clave sea suficientemente larga (32+ bytes para HS256).
    private final String jwtSecret = "MiClaveSecretaSuperLargaYSeguraQueTieneAlMenos512BitsDeLongitud";

    // ms (ahora: 1 minuto de prueba)
    private final long jwtExpiration = 1000L * 60L * 1L;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Usuario usuario) {
        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + jwtExpiration);

        return Jwts.builder()
                .setSubject(usuario.getUsuarioId().toString())            // sub = UUID
                .claim("usuarioId", usuario.getUsuarioId().toString())
                .claim("email", usuario.getEmail())
                .claim("nombre", usuario.getNombre())
                .claim("apellido", usuario.getApellido())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Devuelve true solo si el token es válido y NO está expirado.
     * Si está expirado (ExpiredJwtException) → false (para que caiga en 401 via EntryPoint).
     * Si está mal formado / firma inválida / etc (JwtException) → false.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    // .setAllowedClockSkewSeconds(5) // (opcional) tolerancia de reloj
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // Token expirado → false (Security caerá en AuthenticationEntryPoint -> 401)
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            // Firma inválida, token malformado, vacío, etc.
            return false;
        }
    }

    /**
     * Helper para obtener claims. Si allowExpired=true, permite leer claims de un token expirado
     * (útil para logs); en runtime normal usa allowExpired=false.
     */
    private Claims parseClaims(String token, boolean allowExpired) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            if (allowExpired) {
                return e.getClaims();
            }
            throw e;
        }
    }

    public String getUserIdFromToken(String token) {
        return parseClaims(token, false).getSubject();
    }

    public String getEmailFromToken(String token) {
        return parseClaims(token, false).get("email", String.class);
    }

    public String getNombreFromToken(String token) {
        return parseClaims(token, false).get("nombre", String.class);
    }

    public String getUsuarioIdFromToken(String token) {
        return parseClaims(token, false).get("usuarioId", String.class);
    }
}
