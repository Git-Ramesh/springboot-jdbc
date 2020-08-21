package com.rs.app.dto;

import java.io.Serializable;

import com.rs.app.bo.JdbcBookBo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JdbcBookDto implements Serializable {
	private static final long serialVersionUID = -5941245852633651279L;
	private long id;
	private String title;
	private String author;
	private float price;
	private String publisher;

	public JdbcBookDto(JdbcBookBo jdbcBookBo) {
		this.id = jdbcBookBo.getId();
		this.title = jdbcBookBo.getTitle();
		this.author = jdbcBookBo.getAuthor();
		this.price = jdbcBookBo.getPrice();
		this.publisher = jdbcBookBo.getPublisher();
	}
}
