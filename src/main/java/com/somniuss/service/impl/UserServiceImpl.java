package com.somniuss.service.impl;

import com.somniuss.bean.User;
import com.somniuss.dao.DaoException;
import com.somniuss.dao.DaoProvider;
import com.somniuss.dao.UserDao;
import com.somniuss.service.ServiceException;
import com.somniuss.service.UserService;

public class UserServiceImpl implements UserService {

	private final UserDao userDao = DaoProvider.getInstance().getUserDao();

	@Override
	public User signIn(String name, String password) throws ServiceException {
		try {
			User user = userDao.authorization(name, password);
			return user;
		} catch (DaoException e) {
			throw new ServiceException("Ошибка при аутентификации пользователя", e);
		}
	}

	@Override
	public User registration(String name, String email, String password) throws ServiceException {
		if ("Vasia".equalsIgnoreCase(name)) {
			throw new ServiceException("Имя пользователя 'Vasia' не допускается.");
		}
		try {
			return userDao.registration(name, email, password);
		} catch (DaoException e) {
			throw new ServiceException("Ошибка при регистрации пользователя", e);
		}
	}

	@Override
	public boolean makeUserAuthor(String name, String email) throws ServiceException {
		try {
			return userDao.makeUserAuthor(name, email);
		} catch (DaoException e) {
			throw new ServiceException("Ошибка при обновлении роли пользователя на автора", e);
		}
	}

	@Override
	public boolean isUserExistsByEmail(String email) throws ServiceException {
		try {

			return userDao.isUserExistsByEmail(email);
		} catch (DaoException e) {
			throw new ServiceException("Ошибка при проверке существования пользователя по email", e);
		}
	}

	@Override
	public User getUserByEmail(String email) throws ServiceException {
		try {
			return userDao.getUserByEmail(email);
		} catch (DaoException e) {
			throw new ServiceException("Ошибка при получении пользователя по email", e);
		}
	}

}
