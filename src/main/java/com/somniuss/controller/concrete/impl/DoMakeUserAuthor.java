package com.somniuss.controller.concrete.impl;

import java.io.IOException;

import com.somniuss.bean.User;
import com.somniuss.controller.concrete.Command;
import com.somniuss.service.ServiceException;
import com.somniuss.service.ServiceProvider;
import com.somniuss.service.UserService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DoMakeUserAuthor implements Command {

    private final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        
        if (name == null || email == null || name.isEmpty() || email.isEmpty()) {
            request.setAttribute("error", "Все поля должны быть заполнены.");
            forwardToAuthorPage(request, response);
            return;
        }

        try {
            
            boolean isUpdated = userService.makeUserAuthor(name, email);
            if (isUpdated) {
                
                User updatedUser = userService.getUserByEmail(email); 
                request.getSession().setAttribute("user", updatedUser); 

                response.sendRedirect("MyController?command=go_to_author_page"); 
            } else {
                request.setAttribute("error", "Ошибка при обновлении роли. Попробуйте снова.");
                forwardToAuthorPage(request, response);
            }
        } catch (ServiceException e) {
            
            request.setAttribute("error", "Ошибка при обновлении роли. Попробуйте позже.");
            forwardToAuthorPage(request, response);
        }
    }

    private void forwardToAuthorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/author_registration.jsp");
        dispatcher.forward(request, response);
    }
}
