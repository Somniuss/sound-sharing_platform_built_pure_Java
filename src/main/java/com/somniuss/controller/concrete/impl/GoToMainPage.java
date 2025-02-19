package com.somniuss.controller.concrete.impl;

import java.io.IOException;

import com.somniuss.controller.concrete.Command;
import com.somniuss.bean.User; // Импортируем модель User

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToMainPage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session != null) {

			User user = (User) session.getAttribute("user");

			if (user != null) {
				request.setAttribute("user", user);
			}
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}
}
