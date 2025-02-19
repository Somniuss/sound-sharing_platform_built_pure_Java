package com.somniuss.service;

import com.somniuss.bean.User;

public interface UserService {

	User signIn(String login, String password) throws ServiceException;

	User registration(String name, String email, String password) throws ServiceException;

	boolean makeUserAuthor(String name, String email) throws ServiceException;

	boolean isUserExistsByEmail(String email) throws ServiceException;

	User getUserByEmail(String email) throws ServiceException;

}
