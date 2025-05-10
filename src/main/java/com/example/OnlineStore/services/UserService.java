package com.example.OnlineStore.services;

import com.example.OnlineStore.models.User;
import com.example.OnlineStore.models.enums.Role;
import com.example.OnlineStore.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional
    public void addRoleToUser(String username, Role role) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Set<Role> roles = new HashSet<>(user.getRoles());
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    @Transactional
    public void deleteRoleFromUser(String username, Role role) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Set<Role> roles = new HashSet<>(user.getRoles());
            roles.remove(role);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    public String getPrimaryRole(User user) {
        String primaryRole = "ROLE_USER";
        if (user.getRoles().contains(Role.ROLE_ADMIN)) {
            primaryRole = "ROLE_ADMIN";
        } else if (user.getRoles().contains(Role.ROLE_SELLER)) {
            primaryRole = "ROLE_SELLER";
        }

        return primaryRole;
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
