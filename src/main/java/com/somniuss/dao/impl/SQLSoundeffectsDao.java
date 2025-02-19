package com.somniuss.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.somniuss.bean.Sound;
import com.somniuss.dao.DaoException;
import com.somniuss.dao.SoundeffectsDao;
import com.somniuss.dao.connectionpoolprovider.ConnectionPoolProvider;
import com.somniuss.dao.сonnectionpool.ConnectionPoolException;

public class SQLSoundeffectsDao implements SoundeffectsDao {

    @Override
    public boolean add(Sound sound) throws DaoException {
        final String ADD_SOUND =
                "INSERT INTO sounds (title, description, file_path, category_id, author_id, upload_date, price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
             PreparedStatement ps = con.prepareStatement(ADD_SOUND, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, sound.getTitle());
            ps.setString(2, sound.getDescription());
            ps.setString(3, sound.getFilePath());
            ps.setInt(4, sound.getCategoryId());
            ps.setInt(5, sound.getAuthorId());
            ps.setTimestamp(6, Timestamp.valueOf(sound.getUploadDate())); 
            ps.setBigDecimal(7, sound.getPrice());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        sound.setId(generatedKeys.getInt(1)); 
                        return true;
                    }
                }
            }
            return false;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Ошибка при добавлении звукового эффекта", e);
        }
    }

    @Override
    public boolean update(Sound sound) throws DaoException {
        final String UPDATE_SOUND =
                "UPDATE sounds SET title = ?, description = ?, file_path = ?, category_id = ?, price = ? " +
                "WHERE id = ?";

        try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_SOUND)) {

            ps.setString(1, sound.getTitle());
            ps.setString(2, sound.getDescription());
            ps.setString(3, sound.getFilePath());
            ps.setInt(4, sound.getCategoryId());
            ps.setBigDecimal(5, sound.getPrice());
            ps.setInt(6, sound.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Ошибка при обновлении звукового эффекта", e);
        }
    }

    @Override
    public boolean delete(int id) throws DaoException {
        final String DELETE_SOUND = "DELETE FROM sounds WHERE id = ?";

        try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_SOUND)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Ошибка при удалении звукового эффекта", e);
        }
    }

    @Override
    public Sound getById(int id) throws DaoException {
        final String GET_SOUND_BY_ID =
                "SELECT id, title, description, file_path, category_id, author_id, upload_date, price " +
                "FROM sounds WHERE id = ?";

        try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
             PreparedStatement ps = con.prepareStatement(GET_SOUND_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createSoundFromResultSet(rs);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Ошибка при получении звукового эффекта по ID", e);
        }
        return null;
    }

    @Override
    public List<Sound> getAll() throws DaoException {
        final String GET_ALL_SOUNDS =
                "SELECT id, title, description, file_path, category_id, author_id, upload_date, price FROM sounds";

        List<Sound> sounds = new ArrayList<>();
        try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL_SOUNDS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sounds.add(createSoundFromResultSet(rs));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Ошибка при получении всех звуковых эффектов", e);
        }
        return sounds;
    }

    private Sound createSoundFromResultSet(ResultSet rs) throws SQLException {
        return new Sound(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("file_path"),
                rs.getInt("category_id"),
                rs.getInt("author_id"),
                rs.getTimestamp("upload_date").toLocalDateTime(), 
                rs.getBigDecimal("price")
        );
    }
    
    @Override
    public List<Sound> getGlitchSounds() throws DaoException {
        final String CATEGORY_GLITCH_ID = "1"; // Constant for category ID "glitch"
        final String GET_GLITCH_SOUNDS =
                "SELECT id, title, description, file_path, category_id, author_id, upload_date, price " +
                "FROM sounds WHERE category_id = ?";

        List<Sound> sounds = new ArrayList<>();
        try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
             PreparedStatement ps = con.prepareStatement(GET_GLITCH_SOUNDS)) {

            ps.setString(1, CATEGORY_GLITCH_ID); // Set the constant for the glitch category ID
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sounds.add(createSoundFromResultSet(rs));
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Ошибка при получении звуковых эффектов для глитчей", e);
        }
        return sounds;
    }
    
    @Override
    public List<Sound> getAtmosphereSounds() throws DaoException {
        final String CATEGORY_ATMOSPHERE_ID = "2"; // Constant for category ID "atmosphere"
        final String GET_GLITCH_SOUNDS =
                "SELECT id, title, description, file_path, category_id, author_id, upload_date, price " +
                "FROM sounds WHERE category_id = ?";

        List<Sound> sounds = new ArrayList<>();
        try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
             PreparedStatement ps = con.prepareStatement(GET_GLITCH_SOUNDS)) {

            ps.setString(1, CATEGORY_ATMOSPHERE_ID); 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sounds.add(createSoundFromResultSet(rs));
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Ошибка при получении звуковых эффектов для глитчей", e);
        }
        return sounds;
    }
    
    

}
