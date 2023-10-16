package com.example.aniamlwaruser.config;

import com.example.aniamlwaruser.domain.entity.RefreshToken;
import com.example.aniamlwaruser.domain.entity.User;
import com.example.aniamlwaruser.repository.RefreshTokenRepository;
import com.example.aniamlwaruser.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public String generateNewAccessToken(String refreshToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            String id = claims.getSubject();
            User user = userRepository.findByid(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                    .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

            if (storedToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Refresh token is expired");
            }

            return makeAccessToken(user);
        } catch (Exception e) {
            throw new RuntimeException("Error while refreshing token");
        }
    }


    // userUUID를 이용해 리프레쉬토큰 생성
    public String makeRefreshToken(User user) {
        Date expiryDate = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 3));
        Key signingKey = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setSubject(user.getUserUUID().toString())
                .setExpiration(expiryDate)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }


    // 리프레쉬토큰 DB 저장
    public void saveRefreshToken(User user, String refreshToken) {
        RefreshToken newToken = new RefreshToken();
        newToken.setId(user.getUserUUID().toString());
        newToken.setToken(refreshToken);
        newToken.setExpiryDate(getRefreshTokenExpiryDate(refreshToken));
        refreshTokenRepository.save(newToken);
    }

    public LocalDateTime getRefreshTokenExpiryDate(String refreshToken) {
        Claims body = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
        return body.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    // AccessToken 생성
    public String makeAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("nickName", user.getNickName());

        Key signingKey = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + (1000L * 60 * 20)))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public TokenInfo parseAccessToken(String Accesstoken) {
        Claims body = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(Accesstoken)
                .getBody();


        UUID userUUID = UUID.fromString(body.get("userUUID", String.class));
        User user = userRepository.findById(userUUID).orElse(null);

        if (user == null) {
            throw new RuntimeException("Invalid Token");
        }

        return TokenInfo.builder()
                .userUUID(user.getUserUUID())
                .id(user.getId())
                .nickName(user.getNickName())
                .build();
    }
}
