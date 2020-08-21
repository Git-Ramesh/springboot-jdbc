package com.rs.app.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JdbcBookBo {
	private long id;
	private String title;
	private String author;
	private float price;
	private String publisher;
}
