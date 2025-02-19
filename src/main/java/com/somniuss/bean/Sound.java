package com.somniuss.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Sound implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String title;
	private String description;
	private String filePath;
	private int categoryId;
	private int authorId;
	private LocalDateTime uploadDate;
	private BigDecimal price;

	public Sound() {

	}

	public Sound(int id, String title, String description, String filePath, int categoryId, int authorId,
			LocalDateTime uploadDate, BigDecimal price) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.filePath = filePath;
		this.categoryId = categoryId;
		this.authorId = authorId;
		this.uploadDate = uploadDate;
		this.price = price;
	}

	public Sound(String title, String description, String filePath, int categoryId, int authorId,
			LocalDateTime uploadDate, BigDecimal price) {
		this.title = title;
		this.description = description;
		this.filePath = filePath;
		this.categoryId = categoryId;
		this.authorId = authorId;
		this.uploadDate = uploadDate;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, description, filePath, categoryId, authorId, uploadDate, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Sound other = (Sound) obj;
		return id == other.id && categoryId == other.categoryId && authorId == other.authorId
				&& Objects.equals(title, other.title) && Objects.equals(description, other.description)
				&& Objects.equals(filePath, other.filePath) && Objects.equals(uploadDate, other.uploadDate)
				&& Objects.equals(price, other.price);
	}

	@Override
	public String toString() {
		return "Sound [id=" + id + ", title=" + title + ", description=" + description + ", filePath=" + filePath
				+ ", categoryId=" + categoryId + ", authorId=" + authorId + ", uploadDate=" + uploadDate + ", price="
				+ price + "]";
	}
}
