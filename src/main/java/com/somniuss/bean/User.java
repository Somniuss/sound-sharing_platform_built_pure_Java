package com.somniuss.bean;

import org.mindrot.jbcrypt.BCrypt;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String email;
	private String passwordHash;
	private String name;
	private int roleId; 

	public User() {
	}

	public User(String name, String email, String password, int roleId) {
		this.name = name;
		this.email = email;
		setPassword(password);
		this.roleId = roleId;
	}

	public User(int id, String name, String email, String password, int roleId) {
		this.id = id;
		this.name = name;
		this.email = email;
		setPassword(password);
		this.roleId = roleId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPassword(String password) {
		this.passwordHash = hashPassword(password);
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	private String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		User user = (User) obj;
		return id == user.id && roleId == user.roleId && Objects.equals(name, user.name)
				&& Objects.equals(email, user.email) && Objects.equals(passwordHash, user.passwordHash);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, email, passwordHash, roleId);
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", roleId=" + roleId
				+ '}';
	}
}
