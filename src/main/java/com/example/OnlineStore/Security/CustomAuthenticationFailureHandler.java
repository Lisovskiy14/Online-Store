package com.example.OnlineStore.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String errorMessage = "Невірний логін або пароль!";

        if (exception instanceof LockedException) {
            errorMessage = "Обліковий запис заблоковано!";
        }

        request.getSession().setAttribute("errorMessage", errorMessage);
        response.sendRedirect("/login");
    }
}
