package com.somniuss.dao;

import com.somniuss.bean.Sound;
import java.util.List;

public interface SoundeffectsDao {

	boolean add(Sound sound) throws DaoException;

	boolean update(Sound sound) throws DaoException;

	boolean delete(int id) throws DaoException;

	Sound getById(int id) throws DaoException;

	List<Sound> getAll() throws DaoException;

	List<Sound> getGlitchSounds() throws DaoException;

	List<Sound> getAtmosphereSounds() throws DaoException;
}
