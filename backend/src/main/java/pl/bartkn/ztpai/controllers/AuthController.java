package pl.bartkn.ztpai.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.bartkn.ztpai.model.dto.request.auth.LoginRequest;
import pl.bartkn.ztpai.model.dto.request.auth.RegisterRequest;
import pl.bartkn.ztpai.model.dto.response.AuthResponse;
import pl.bartkn.ztpai.model.entity.User;
import pl.bartkn.ztpai.service.UserAuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest userCredentials) {
        return ResponseEntity.ok().body(userAuthService.login(userCredentials));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest userCredentials) {
        userAuthService.register(userCredentials);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    public void logout() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userAuthService.logout(user);
    }

    @DeleteMapping("/delete")
    public void deleteAccount() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userAuthService.delete(user);
    }
}
