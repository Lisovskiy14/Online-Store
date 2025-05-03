package com.example.OnlineStore.services;

import com.example.OnlineStore.models.User;
import com.example.OnlineStore.models.enums.Role;
import com.example.OnlineStore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user, boolean isSeller) {
        String username = user.getUsername();
        if (userRepository.findByUsername(username) != null) {
            return false;
        }

        System.out.println("Creating user with username: " + username);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        if (isSeller) {
            user.getRoles().add(Role.ROLE_SELLER);
        }
        userRepository.save(user);

        System.out.println("User created with username: " + username);

        return true;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
