package ru.ponomarchuk.llmchat.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ponomarchuk.llmchat.dto.*;
import ru.ponomarchuk.llmchat.entity.User;
import ru.ponomarchuk.llmchat.repository.UserRepository;
import ru.ponomarchuk.llmchat.util.JwtUtils;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public AuthResponse register(RegisterRequest request) {
        AuthResponse errorResponse = new AuthResponse();

        if (userRepository.existsByUserLogin(request.getLogin())) {
            errorResponse.setStatus("Error");
            errorResponse.setMessage("Ошибка регистрации - логин занят");
            return errorResponse;
        }

        if (request.getLogin().length() > 100 || request.getPassword().length() > 100) {
            errorResponse.setStatus("Error");
            errorResponse.setMessage("Длина логина и пароля должна быть не более 100 символов");
            return errorResponse;
        }

        User user = new User();
        user.setUserLogin(request.getLogin());
        user.setUserEncodedPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String token = jwtUtils.generateToken(user.getId());

        AuthResponse response = new AuthResponse();
        response.setStatus("Success");
        response.setUserId(user.getId());
        response.setAccessToken(token);
        return response;
    }

    public AuthResponse login(LoginRequest request) {
        AuthResponse errorResponse = new AuthResponse();

        User user = userRepository.findByUserLogin(request.getLogin())
                .orElse(null);

        if (user == null) {
            errorResponse.setStatus("Error");
            errorResponse.setMessage("Ошибка входа - проверьте логин");
            return errorResponse;
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getUserEncodedPassword())) {
            errorResponse.setStatus("Error");
            errorResponse.setMessage("Ошибка входа - неправильный пароль");
            return errorResponse;
        }

        String token = jwtUtils.generateToken(user.getId());

        AuthResponse response = new AuthResponse();
        response.setStatus("Success");
        response.setUserId(user.getId());
        response.setAccessToken(token);
        return response;
    }
}
