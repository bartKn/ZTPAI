package pl.bartkn.ztpai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bartkn.ztpai.exception.TokenNotFoundException;
import pl.bartkn.ztpai.model.dto.response.RefreshResponse;
import pl.bartkn.ztpai.model.entity.RefreshToken;
import pl.bartkn.ztpai.repository.TokenRepository;
import pl.bartkn.ztpai.security.jwt.JwtService;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public RefreshResponse refresh(String refreshToken) {
        return tokenRepository.findByToken(refreshToken)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtService.generateToken(user);
                    return new RefreshResponse(token, refreshToken);
                })
                .orElseThrow(TokenNotFoundException::new);
    }
}
