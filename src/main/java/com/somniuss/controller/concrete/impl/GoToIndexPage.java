package com.somniuss.controller.concrete.impl;

import com.somniuss.bean.News;
import com.somniuss.controller.concrete.Command;
import com.somniuss.service.NewsService;
import com.somniuss.service.ServiceException;
import com.somniuss.service.ServiceProvider;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToIndexPage implements Command {
	
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService(); // Ничего нового не случилось, поэтому не отображаются

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<News> newsList = newsService.getAllNews();
			request.setAttribute("news", newsList);
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", "Ошибка при загрузке новостей. Попробуйте позже.");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main_index.jsp");
		dispatcher.forward(request, response);
	}
}
