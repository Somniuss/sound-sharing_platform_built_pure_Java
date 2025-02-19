package com.somniuss.dao.impl;

import java.sql.*;

import org.mindrot.jbcrypt.BCrypt;

import com.somniuss.bean.User;
import com.somniuss.dao.DaoException;
import com.somniuss.dao.UserDao;
import com.somniuss.dao.connectionpoolprovider.ConnectionPoolProvider;
import com.somniuss.dao.сonnectionpool.ConnectionPoolException;

public class SQLUserDao implements UserDao {

	private static final String CHECK_USER_EXISTENCE = "SELECT COUNT(*) FROM users WHERE email = ? OR name = ?";

	private static final String REGISTRATION = "INSERT INTO users (email, password, name, role_id) VALUES (?, ?, ?, ?)";

	public static final int DEFAULT_ROLE_ID = 1;

	@Override
	public User registration(String name, String email, String password) throws DaoException {
		String passwordHash = hashPassword(password);

		try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection()) {

			boolean userExists;
			try (PreparedStatement psCheck = con.prepareStatement(CHECK_USER_EXISTENCE)) {
				psCheck.setString(1, email);
				psCheck.setString(2, name);

				try (ResultSet rs = psCheck.executeQuery()) {
					userExists = rs.next() && rs.getInt(1) > 0;
				}
			}

			if (userExists) {
				throw new DaoException("Пользователь с таким email или именем уже существует.");
			}

			try (PreparedStatement psUser = con.prepareStatement(REGISTRATION, Statement.RETURN_GENERATED_KEYS)) {
				psUser.setString(1, email);
				psUser.setString(2, passwordHash);
				psUser.setString(3, name);
				psUser.setInt(4, DEFAULT_ROLE_ID);

				int rowsAffected = psUser.executeUpdate();
				if (rowsAffected > 0) {
					try (ResultSet generatedKeys = psUser.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							int userId = generatedKeys.getInt(1);
							return new User(userId, name, email, passwordHash, DEFAULT_ROLE_ID);
						}
					}
				}
				throw new DaoException("Ошибка при добавлении пользователя.");
			}

		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoException("Ошибка при регистрации пользователя", e);
		}
	}

	private static final String IS_USER_EXIST_BY_NAME = "SELECT 1 FROM users WHERE name = ?";

	@Override
	public boolean isUserExistsByName(String name) throws DaoException {
		try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
				PreparedStatement ps = con.prepareStatement(IS_USER_EXIST_BY_NAME)) {
			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("Ошибка проверки существования пользователя по имени", e);
		}
	}

	private static final String IS_USER_EXIST_BY_EMAIL = "SELECT 1 FROM users WHERE email = ?";

	@Override
	public boolean isUserExistsByEmail(String email) throws DaoException {
		try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
				PreparedStatement ps = con.prepareStatement(IS_USER_EXIST_BY_EMAIL)) {
			ps.setString(1, email);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("Ошибка проверки существования пользователя по email", e);
		}
	}

	private static final String AUTHORISATION = "SELECT id, name, email, password, role_id FROM users WHERE name = ?";

	@Override
	public User authorization(String name, String password) throws DaoException {
		try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
				PreparedStatement ps = con.prepareStatement(AUTHORISATION)) {

			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String passwordHash = rs.getString("password");
					if (checkPassword(password, passwordHash)) {
						int userId = rs.getInt("id");
						String email = rs.getString("email");
						int roleId = rs.getInt("role_id");

						return new User(userId, name, email, passwordHash, roleId);
					}
				}
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("Ошибка при авторизации пользователя", e);
		}

		return null;
	}

	private static final String UPDATE_USER_ROLE = "UPDATE users SET role_id = ? WHERE id = ?";
	private static final String GET_USER_ROLE_BY_EMAIL_NAME = "SELECT id, role_id FROM users WHERE email = ? AND name = ?";

	public static final int AUTHOR_ROLE_ID = 2;

	@Override
	public boolean makeUserAuthor(String name, String email) throws DaoException {
		try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
				PreparedStatement psGetUser = con.prepareStatement(GET_USER_ROLE_BY_EMAIL_NAME)) {

			psGetUser.setString(1, email);
			psGetUser.setString(2, name);

			try (ResultSet rs = psGetUser.executeQuery()) {
				if (rs.next()) {
					int userId = rs.getInt("id");

					try (PreparedStatement psUpdateRole = con.prepareStatement(UPDATE_USER_ROLE)) {
						psUpdateRole.setInt(1, AUTHOR_ROLE_ID);
						psUpdateRole.setInt(2, userId);

						return psUpdateRole.executeUpdate() > 0;
					}
				}
			}
		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoException("Ошибка при обновлении роли пользователя на автора", e);
		}

		return false;
	}

	private static final String GET_USER_BY_EMAIL = "SELECT id, name, email, password, role_id FROM users WHERE email = ?";

	@Override
	public User getUserByEmail(String email) throws DaoException {
		try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
				PreparedStatement ps = con.prepareStatement(GET_USER_BY_EMAIL)) {

			ps.setString(1, email);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int userId = rs.getInt("id");
					String name = rs.getString("name");
					String password = rs.getString("password");
					int roleId = rs.getInt("role_id");

					return new User(userId, name, email, password, roleId);
				}
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("Ошибка при получении пользователя по email", e);
		}

		return null;
	}

	private String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	private boolean checkPassword(String password, String passwordHash) {
		return BCrypt.checkpw(password, passwordHash);
	}
}
