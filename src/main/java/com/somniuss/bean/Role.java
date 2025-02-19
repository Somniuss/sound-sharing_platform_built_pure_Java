package com.somniuss.bean;

import java.io.Serializable;
import java.util.Objects;

public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public Role(int id, String name) {
		this.id = id;
		this.name = name;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Role role = (Role) obj;
		return id == role.id && Objects.equals(name, role.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "Role{" + "id=" + id + ", name='" + name + '\'' + '}';
	}
}
