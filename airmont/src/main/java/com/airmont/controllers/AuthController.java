package com.airmont.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airmont.dto.LoginRequest;
import com.airmont.dto.LoginResponse;
import com.airmont.dto.LogoutRequest;
import com.airmont.dto.UserResponse;
import com.airmont.models.entity.AdminUser;
import com.airmont.repositories.AdminUserRepository;
import com.airmont.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AdminUserRepository adminUserRepository;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AdminUser adminUser) {
        Optional<AdminUser> userOpt = authService.registerUser(adminUser);
        
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(new UserResponse(userOpt.get()));
        } else {
            // Retorna un objeto JSON con el mensaje
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "El usuario ya existe");
            return ResponseEntity.status(409).body(errorResponse);
        }
    }



    	
 // Endpoint para verificar si ya existe un usuario registrado
    @GetMapping("/checkUserExists")
    public ResponseEntity<Boolean> checkUserExists() {
        boolean exists = authService.userExists();
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/login")	
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/changePassword")
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody Map<String, String> passwordData) {
        String email = passwordData.get("email");
        String currentPassword = passwordData.get("currentPassword");
        String newPassword = passwordData.get("newPassword");

        String responseMessage = authService.changePassword(email, currentPassword, newPassword);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", responseMessage);

        if ("Contraseña actualizada con éxito".equals(responseMessage)) {
            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.status(400).body(responseBody);
        }
    }
    


    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestBody LogoutRequest logoutRequest) {
        Optional<AdminUser> userOpt = adminUserRepository.findByEmail(logoutRequest.getEmail());
        
        if (userOpt.isPresent()) {
            authService.logoutUser(userOpt.get());
            return ResponseEntity.ok("Sesión cerrada con éxito");
        }
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }
    
    @PostMapping("/clearSessionToken")
    public ResponseEntity<String> clearSessionToken() {
        try {
            authService.clearSessionToken();
            return ResponseEntity.ok("Token de sesión eliminado con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @Scheduled(fixedRate = 900000) // Ejecuta cada 15 minutos
    public void expireTokens() {
        authService.expireTokens();
    }
    
    @PostMapping("/validateToken")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody Map<String, String> tokenData) {
        String token = tokenData.get("token");
        boolean isValid = authService.validateToken(token);

        Map<String, Object> response = new HashMap<>();
        response.put("isValid", isValid);

        if (isValid) {
            response.put("message", "Token válido");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Token inválido o expirado");
            return ResponseEntity.status(401).body(response);
        }
    }

}

