package pl.bartkn.ztpai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bartkn.ztpai.exception.TokenExpiredException;
import pl.bartkn.ztpai.exception.TokenNotFoundException;
import pl.bartkn.ztpai.model.dto.response.RefreshResponse;
import pl.bartkn.ztpai.model.entity.RefreshToken;
import pl.bartkn.ztpai.repository.TokenRepository;
import pl.bartkn.ztpai.security.jwt.JwtService;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public RefreshResponse refresh(String refreshToken) {
        RefreshToken token = tokenRepository.findByToken(refreshToken).orElseThrow(TokenNotFoundException::new);
        if (token.getExpiryDate().after(new Date(System.currentTimeMillis()))) {
            String newToken = jwtService.generateToken(token.getUser());
            return new RefreshResponse(newToken, refreshToken);
        } else {
            throw new TokenExpiredException();
        }
    }
}
