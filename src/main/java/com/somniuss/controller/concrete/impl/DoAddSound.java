package com.somniuss.controller.concrete.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.somniuss.bean.Sound;
import com.somniuss.controller.concrete.Command;
import com.somniuss.service.ServiceException;
import com.somniuss.service.ServiceProvider;
import com.somniuss.service.SoundeffectsService;
import com.somniuss.service.impl.SoundeffectsServiceImpl;
import com.somniuss.bean.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpSession;

public class DoAddSound implements Command {

    private final SoundeffectsService soundService = ServiceProvider.getInstance().getSoundeffectsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String categoryIdStr = request.getParameter("categoryId");
        String priceStr = request.getParameter("price");
        Part filePart = request.getPart("sfxFile"); 

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            request.setAttribute("error", "Вы должны быть авторизованы, чтобы загружать звуки.");
            request.getRequestDispatcher("WEB-INF/jsp/main_index.jsp").forward(request, response);
            return;
        }

        
        if (title == null || title.isEmpty() || categoryIdStr == null || priceStr == null || filePart == null) {
            request.setAttribute("error", "Все поля должны быть заполнены.");
            request.getRequestDispatcher("WEB-INF/jsp/author_page.jsp").forward(request, response);
            return;
        }

        int categoryId;
        BigDecimal price;
        try {
            categoryId = Integer.parseInt(categoryIdStr);
            price = new BigDecimal(priceStr);
            if (price.compareTo(BigDecimal.ZERO) < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Некорректный формат цены или категории.");
            request.getRequestDispatcher("WEB-INF/jsp/author_page.jsp").forward(request, response);
            return;
        }

        String fileName = filePart.getSubmittedFileName();
        if (!fileName.endsWith(".mp3")) {
            request.setAttribute("error", "Файл должен быть в формате MP3.");
            request.getRequestDispatcher("WEB-INF/jsp/author_page.jsp").forward(request, response);
            return;
        }

        Sound sound = new Sound();
        sound.setTitle(title);
        sound.setDescription(description);
        sound.setCategoryId(categoryId);
        sound.setPrice(price);
        sound.setUploadDate(LocalDateTime.now()); 
        sound.setAuthorId(user.getId()); 

        // Получаем абсолютный путь к корню веб-приложения
        String appPath = request.getServletContext().getRealPath("/");
        // Формируем путь к папке sounds внутри приложения
        String uploadPath = appPath + "sounds" + File.separator;
        
        // Передаём путь в сервис, если он является экземпляром SoundeffectsServiceImpl
        if (soundService instanceof SoundeffectsServiceImpl) {
            ((SoundeffectsServiceImpl) soundService).setUploadDir(uploadPath);
        }

        try {
            soundService.add(sound, filePart);
            response.sendRedirect("MyController?command=go_to_main_page");
        } catch (ServiceException e) {
            e.printStackTrace();
            request.setAttribute("error", "Ошибка при добавлении саундэффекта. Попробуйте позже.");
            request.getRequestDispatcher("WEB-INF/jsp/author_page.jsp").forward(request, response);
        }
    }
}
