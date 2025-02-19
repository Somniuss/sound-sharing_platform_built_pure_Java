package com.somniuss.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.somniuss.bean.Sound;
import com.somniuss.dao.DaoException;
import com.somniuss.dao.DaoProvider;
import com.somniuss.dao.SoundeffectsDao;
import com.somniuss.service.ServiceException;
import com.somniuss.service.SoundeffectsService;

import jakarta.servlet.http.Part;

public class SoundeffectsServiceImpl implements SoundeffectsService {

    private final SoundeffectsDao soundeffectsDao = DaoProvider.getInstance().getSoundeffectsDao();
    
    // Теперь uploadDir хранится в виде поля, которое будем задавать из контроллера
    private String uploadDir;

    // Можно добавить сеттер для установки пути
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    @Override
    public boolean add(Sound sound) throws ServiceException {
        try {
            return soundeffectsDao.add(sound);
        } catch (DaoException e) {
            throw new ServiceException("Ошибка при добавлении саундэффекта", e);
        }
    }

    @Override
    public boolean add(Sound sound, Part filePart) throws ServiceException {
        try {
            // Проверяем, что uploadDir задан
            if (uploadDir == null || uploadDir.isEmpty()) {
                throw new ServiceException("Каталог загрузки не настроен.");
            }
            // Генерируем путь к файлу и сохраняем его
            String filePath = saveFile(filePart);
            sound.setFilePath(filePath); // Устанавливаем путь в объект Sound

            // Добавляем информацию о звуке в БД
            return soundeffectsDao.add(sound);
        } catch (DaoException | IOException e) {
            throw new ServiceException("Ошибка при добавлении саундэффекта", e);
        }
    }

    @Override
    public boolean update(Sound sound) throws ServiceException {
        try {
            return soundeffectsDao.update(sound);
        } catch (DaoException e) {
            throw new ServiceException("Ошибка при обновлении саундэффекта", e);
        }
    }

    @Override
    public boolean delete(int id) throws ServiceException {
        try {
            return soundeffectsDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("Ошибка при удалении саундэффекта", e);
        }
    }

    @Override
    public Sound getById(int id) throws ServiceException {
        try {
            return soundeffectsDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException("Ошибка при получении саундэффекта по ID", e);
        }
    }

    @Override
    public List<Sound> getAll() throws ServiceException {
        try {
            return soundeffectsDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException("Ошибка при получении списка саундэффектов", e);
        }
    }
    
    @Override
    public List<Sound> getGlitchSound() throws ServiceException {
        try {
            return soundeffectsDao.getGlitchSounds();
        } catch (DaoException e) {
            throw new ServiceException("Ошибка при получении списка саундэффектов", e);
        }
    }
    
    @Override
    public List<Sound> getAtmosphereSound() throws ServiceException {
        try {
            return soundeffectsDao.getAtmosphereSounds();
        } catch (DaoException e) {
            throw new ServiceException("Ошибка при получении списка саундэффектов", e);
        }
    }
    
    /**
     * Метод сохраняет загруженный MP3 файл в папку [корень_приложения]/sounds/
     * и возвращает путь к сохранённому файлу (относительный URL).
     */
    private String saveFile(Part filePart) throws IOException {
        String fileName = Path.of(filePart.getSubmittedFileName()).getFileName().toString();
        Path destination = Paths.get(uploadDir, fileName);

        Files.createDirectories(destination.getParent());
        Files.copy(filePart.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        // Возвращаем относительный путь для доступа через URL (например, "sounds/yourfile.mp3")
        return "sounds/" + fileName;
    }
}
