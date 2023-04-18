package pl.bartkn.ztpai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bartkn.ztpai.exception.EmailOrUsernameTakenException;
import pl.bartkn.ztpai.model.dto.request.LoginRequest;
import pl.bartkn.ztpai.model.dto.request.RegisterRequest;
import pl.bartkn.ztpai.model.dto.response.AuthResponse;
import pl.bartkn.ztpai.model.entity.RefreshToken;
import pl.bartkn.ztpai.model.entity.Role;
import pl.bartkn.ztpai.model.entity.User;
import pl.bartkn.ztpai.model.mapper.UserMapper;
import pl.bartkn.ztpai.repository.RoleRepository;
import pl.bartkn.ztpai.repository.TokenRepository;
import pl.bartkn.ztpai.repository.UserRepository;
import pl.bartkn.ztpai.security.jwt.JwtService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;

    private final JwtService jwtService;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        List<String> userRoles = new ArrayList<>();
        User user = userRepository.getUserByEmail(loginRequest.getEmail());
        user.getAuthorities().forEach(role -> userRoles.add(role.getAuthority()));
        String token = jwtService.generateToken(user);
        RefreshToken refreshToken = jwtService.generateRefreshToken(user);
        refreshToken.setUser(user);
        tokenRepository.save(refreshToken);
        return new AuthResponse(token, refreshToken.getToken(), userRoles);
    }

    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmailOrUsername(
                registerRequest.getEmail(),
                registerRequest.getUsername()
        )) {
            throw new EmailOrUsernameTakenException();
        }
        User user = userMapper.map(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.getRoles().add(userRole);
        userRepository.save(user);
    }

    public void logout(String token) {
        tokenRepository.deleteByToken(token);
    }

    public void delete(String token) {
        User user = getUserFromToken(token);
        userRepository.delete(user);
    }

    private User getUserFromToken(String token) {
        String email = jwtService.extractUsername(token);
        return userRepository.getUserByEmail(email);
    }
}
