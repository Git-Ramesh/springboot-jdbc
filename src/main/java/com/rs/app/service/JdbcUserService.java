package com.rs.app.service;

import java.io.IOException;
import java.util.List;

import com.rs.app.dto.JdbcUserDto;

public interface JdbcUserService {
	JdbcUserDto addJdbcUser(JdbcUserDto jdbcUserDto);

	JdbcUserDto getJdbcUser(long id);
	
	List<JdbcUserDto> getAllJdbcUsers();

	JdbcUserDto updateJdbcUser(JdbcUserDto jdbcUserDto);
	
	void transactionTest(long id) throws IOException;
}
