package com.hotelgo.service;

import com.hotelgo.model.User;
import com.hotelgo.repository.UserRepository;
import com.hotelgo.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public String register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Email is registered";
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
        return "Registration successful";
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
            return "Incorrect email";
        userRepository.updatePassword(email, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        return "Password updated successfully";
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String updateUser(User user) {
        User existing = userRepository.findByEmail(user.getEmail());
        if (existing != null && !existing.getUsername().equals(user.getUsername())) {
            return "ERROR: Email is already in use";
        }

        boolean updated = userRepository.updateUser(user);
        if (updated) {
            return "SUCCESS: User updated successfully";
        } else {
            return "ERROR: Failed to update user";
        }
    }
}
