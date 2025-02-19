package com.somniuss.controller.concrete.impl;

import java.io.IOException;

import com.somniuss.bean.User;
import com.somniuss.controller.concrete.Command;
import com.somniuss.service.ServiceException;
import com.somniuss.service.UserService;
import com.somniuss.service.ServiceProvider;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoAuth implements Command {

    private final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || login.isEmpty() || login.length() < 3 || login.length() > 50) {
            request.setAttribute("authError", "Логин должен быть от 3 до 50 символов!");
            forwardToPage(request, response, "index.jsp");
            return;
        }

        if (password == null || password.isEmpty() || password.length() < 6 || password.length() > 20) {
            request.setAttribute("authError", "Пароль должен быть от 6 до 20 символов!");
            forwardToPage(request, response, "index.jsp");
            return;
        }

        if (!login.matches("[a-zA-Z0-9]+")) {
            request.setAttribute("authError", "Логин может содержать только буквы и цифры!");
            forwardToPage(request, response, "index.jsp");
            return;
        }

        try {
            User user = userService.signIn(login, password);
            if (user != null) {
                HttpSession oldSession = request.getSession(false);
                if (oldSession != null) {
                    oldSession.invalidate(); 
                }

                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);

                forwardToPage(request, response, "WEB-INF/jsp/main.jsp");
            } else {
                request.setAttribute("authError", "Неправильный логин или пароль!");
                forwardToPage(request, response, "index.jsp");
            }
        } catch (ServiceException e) {
            request.setAttribute("authError", "Ошибка при аутентификации. Попробуйте позже.");
            forwardToPage(request, response, "index.jsp");
        }
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
