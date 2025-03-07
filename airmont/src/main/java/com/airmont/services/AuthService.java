package com.airmont.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airmont.dto.LoginResponse;
import com.airmont.dto.UserResponse;
import com.airmont.models.entity.AdminUser;
import com.airmont.models.entity.SessionToken;
import com.airmont.repositories.AdminUserRepository;
import com.airmont.repositories.SessionTokenRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private SessionTokenRepository sessionTokenRepository;
    
 // Método para verificar si ya existe un usuario registrado en la base de datos
    public boolean userExists() {
        return adminUserRepository.count() > 0;
    }
    
    public UserResponse getUserByEmail(String email) {
        Optional<AdminUser> userOptional = adminUserRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return new UserResponse(userOptional.get());
        } else {
            throw new RuntimeException("Usuario no encontrado con el email: " + email);
        }
    }
    
 // Método para registrar usuario verificando si ya existe
    public Optional<AdminUser> registerUser(AdminUser adminUser) {
    	if (adminUserRepository.count() > 0 || adminUserRepository.findByEmail(adminUser.getEmail()).isPresent()) {
    	    return Optional.empty();
    	}
        
        adminUser.setPassword(encryptPassword(adminUser.getPassword())); // Encripta la contraseña
        AdminUser savedUser = adminUserRepository.save(adminUser);
        return Optional.of(savedUser); // Retorna el usuario registrado
    }

    public LoginResponse loginUser(String email, String password) {
        Optional<AdminUser> userOpt = adminUserRepository.findByEmail(email);

        if (userOpt.isPresent() && verifyPassword(password, userOpt.get().getPassword())) {
            // Eliminar tokens existentes para el usuario
            sessionTokenRepository.deleteByAdminUser(userOpt.get());

            // Crear y guardar nuevo token
            String token = generateToken();
            SessionToken sessionToken = new SessionToken();
            sessionToken.setToken(token);
            sessionToken.setAdminUser(userOpt.get());
            sessionToken.setCreatedAt(LocalDateTime.now());
            sessionTokenRepository.save(sessionToken);

            AdminUser user = userOpt.get();
            return new LoginResponse(true, token, "Login exitoso", user.getEmail(), user.getUsername());
        }
        return new LoginResponse(false, null, "Credenciales inválidas", null, null);
    }

    
    @Transactional
    public String changePassword(String email, String currentPassword, String newPassword) {
        Optional<AdminUser> userOpt = adminUserRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            AdminUser user = userOpt.get();
            
            // Verifica si la contraseña actual es correcta
            if (verifyPassword(currentPassword, user.getPassword())) {
                // Verifica que la nueva contraseña cumpla con los requisitos
                String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,12}$";
                if (newPassword.matches(passwordPattern)) {
                    // Actualiza la contraseña
                    user.setPassword(encryptPassword(newPassword));
                    adminUserRepository.save(user);
                    return "Contraseña actualizada con éxito";
                } else {
                    return "La nueva contraseña no cumple con los requisitos";
                }
            } else {
                return "La contraseña actual es incorrecta";
            }
        }
        return "Usuario no encontrado";
    }

    @Transactional
    public void logoutUser(AdminUser adminUser) {
    	try {
            sessionTokenRepository.deleteByAdminUser(adminUser);
        } catch (Exception e) {
            System.err.println("Error al cerrar sesión: " + e.getMessage());
            throw new RuntimeException("Error al cerrar sesión");
        }
    }
    
    @Transactional
    public void clearSessionToken() {
        Optional<SessionToken> existingToken = sessionTokenRepository.findFirstByOrderByCreatedAtDesc();
        if (existingToken.isPresent()) {
            try {
                sessionTokenRepository.delete(existingToken.get());
            } catch (Exception e) {
                System.err.println("Error al eliminar el token de sesión: " + e.getMessage());
                throw new RuntimeException("Error al eliminar el token de sesión");
            }
        } else {
            throw new RuntimeException("No hay token de sesión activo para eliminar");
        }
    }
    
    public void expireTokens() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(10);
        sessionTokenRepository.findAll().stream()
            .filter(token -> token.getCreatedAt().isBefore(expirationTime))
            .forEach(sessionTokenRepository::delete);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private String encryptPassword(String password) {
        // Genera un hash bcrypt a partir de la contraseña
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean verifyPassword(String rawPassword, String encryptedPassword) {
        // Compara la contraseña en texto plano con el hash almacenado
        return BCrypt.checkpw(rawPassword, encryptedPassword);
    }
    
    public boolean validateToken(String token) {
        Optional<SessionToken> sessionTokenOpt = sessionTokenRepository.findByToken(token);

        if (sessionTokenOpt.isPresent()) {
            SessionToken sessionToken = sessionTokenOpt.get();
            // Verificar si el token ha expirado
            LocalDateTime expirationTime = sessionToken.getCreatedAt().plusMinutes(10);
            if (LocalDateTime.now().isBefore(expirationTime)) {
                return true; // El token es válido
            } else {
                // Eliminar el token si está expirado
                sessionTokenRepository.delete(sessionToken);
            }
        }
        return false; // Token inválido o expirado
    }

}