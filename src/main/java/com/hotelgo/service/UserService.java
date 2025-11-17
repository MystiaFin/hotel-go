package com.hotelgo.service;

import com.hotelgo.model.User;
import com.hotelgo.repository.UserRepository;
import com.hotelgo.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public String register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Email sudah terdaftar!";
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
        return "Registrasi berhasil!";
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            return null;
        }
        return JwtUtil.generateToken(user.getUsername(), user.getRole());
    }

    public String forgotPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return "Email tidak ditemukan";
        userRepository.updatePassword(email, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        return "Password berhasil diperbarui";
    }
}
