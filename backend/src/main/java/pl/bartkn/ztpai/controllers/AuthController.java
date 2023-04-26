package pl.bartkn.ztpai.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bartkn.ztpai.model.dto.request.LoginRequest;
import pl.bartkn.ztpai.model.dto.request.RegisterRequest;
import pl.bartkn.ztpai.model.dto.response.AuthResponse;
import pl.bartkn.ztpai.service.UserAuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest userCredentials) {
        return ResponseEntity.ok().body(userAuthService.login(userCredentials));
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest userCredentials) {
        userAuthService.register(userCredentials);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/auth/logout")
    public void logout(@RequestHeader(name = "Authorization") String token) {
        System.out.println(token);
        userAuthService.logout(token);
    }

    @DeleteMapping("/api/auth/delete")
    public void deleteAccount(@RequestHeader(name = "Authorization") String token) {
        userAuthService.delete(token);
    }
}
