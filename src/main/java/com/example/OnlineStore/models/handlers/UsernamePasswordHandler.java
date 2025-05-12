package com.example.OnlineStore.models.handlers;

import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordHandler {

     public void isPasswordValid(String password) {
         if (password.length() < 4 || password.length() > 50) {
             throw new RuntimeException("Довжина паролю має бути в діапазоні від 4 до 50 символів!");
         }
         if (password.matches(".*\\s.*")) {
             throw new RuntimeException("Пароль не повинен містити пробілів!");
         }
         if (!password.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]*$")) {
             throw new RuntimeException("Пароль повинен містити тільки латинські букви, цифри та спецсимволи!");
         }
     }

     public void isUsernameValid(String username) {
         if (username.length() < 3 || username.length() > 50) {
             throw new RuntimeException("Довжина логіну має бути в діапазоні від 4 до 50 символів!");
         }
         if (username.matches(".*\\s.*")) {
             throw new RuntimeException("Логін не повинен містити пробілів!");
         }
         if (!username.matches("^[a-zA-Z0-9_]*$")) {
             throw new RuntimeException("Логін повинен містити тільки латинські букви, цифри та знак підкреслення!");
         }
         if (username.matches("^[0-9_]*$")) {
             throw new RuntimeException("Логін не може складатися тільки з цифр та/або знаків підкреслення!");
         }

     }
}
