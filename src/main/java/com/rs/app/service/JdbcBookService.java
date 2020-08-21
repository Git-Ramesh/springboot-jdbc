package com.rs.app.service;

import java.util.List;

import com.rs.app.dto.JdbcBookDto;

public interface JdbcBookService {
	JdbcBookDto save(JdbcBookDto jdbcBookDto);

	List<JdbcBookDto> getAll();

	JdbcBookDto get(long id);

	void registerBooks(JdbcBookDto o1, JdbcBookDto o2);
}
