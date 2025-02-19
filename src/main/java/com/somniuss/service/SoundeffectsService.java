package com.somniuss.service;

import com.somniuss.bean.Sound;

import jakarta.servlet.http.Part;

import java.util.List;

public interface SoundeffectsService {


    boolean update(Sound sound) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    Sound getById(int id) throws ServiceException;

    List<Sound> getAll() throws ServiceException;
    
    public boolean add(Sound sound, Part filePart) throws ServiceException;
    
    boolean add(Sound sound) throws ServiceException;

	List<Sound> getGlitchSound() throws ServiceException;

	List<Sound> getAtmosphereSound() throws ServiceException;

}
