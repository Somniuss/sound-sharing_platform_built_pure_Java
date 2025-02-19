package com.somniuss.controller.concrete.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.somniuss.bean.Sound;
import com.somniuss.controller.concrete.Command;
import com.somniuss.service.ServiceException;
import com.somniuss.service.ServiceProvider;
import com.somniuss.service.SoundeffectsService;
import com.somniuss.service.impl.SoundeffectsServiceImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToAtmospherePage implements Command {

    private final SoundeffectsService soundeffectsService = ServiceProvider.getInstance().getSoundeffectsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String appPath = request.getServletContext().getRealPath("/");
        String uploadPath = appPath + "sounds" + File.separator;
        
        
        if (soundeffectsService instanceof SoundeffectsServiceImpl) {
            ((SoundeffectsServiceImpl) soundeffectsService).setUploadDir(uploadPath);
        }
        
        try {
            List<Sound> atmosphereSounds = soundeffectsService.getAtmosphereSound();
            request.setAttribute("atmosphereSounds", atmosphereSounds);
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", "Ошибка при загрузке атмосферных звуков");
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/atmosphere.jsp");
        dispatcher.forward(request, response);
    }
}
