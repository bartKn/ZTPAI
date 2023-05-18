package pl.bartkn.ztpai.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.bartkn.ztpai.model.entity.RefreshToken;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "50655368566D597133743677397A24432646294A404E635166546A576E5A7234";
    private final int LIFESPAN = 1000 * 60 * 60 * 24; // 60 seconds * 60 * 24

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Date creationTime = new Date(System.currentTimeMillis());
        Date expiryTime = new Date(System.currentTimeMillis() + LIFESPAN);
        return generateToken(extraClaims, userDetails, creationTime, expiryTime);
    }

    private String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            Date creationTime,
            Date expiryTime
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(creationTime)
                .setExpiration(expiryTime)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public RefreshToken generateRefreshToken(UserDetails userDetails) {
        Date creationTime = new Date(System.currentTimeMillis());
        Date expiryTime = new Date(System.currentTimeMillis() + LIFESPAN * 5);
        String token = generateToken(new HashMap<>(), userDetails, creationTime, expiryTime);
        return RefreshToken
                .builder()
                .token(token)
                .expiryDate(expiryTime)
                .build();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
