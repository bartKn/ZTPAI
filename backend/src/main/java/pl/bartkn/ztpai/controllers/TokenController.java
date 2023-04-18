package pl.bartkn.ztpai.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.bartkn.ztpai.model.dto.response.RefreshResponse;
import pl.bartkn.ztpai.service.TokenService;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/token/refresh")
    public ResponseEntity<RefreshResponse> refreshToken(@RequestBody String refreshToken) {
        return ResponseEntity.ok().body(tokenService.refresh(refreshToken));
    }
}
