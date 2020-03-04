package com.rs.app.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JdbcUserDto implements Serializable {
	private static final long serialVersionUID = 1172894798446595512L;
	private long id;
	private String name;
	private String email;
} 