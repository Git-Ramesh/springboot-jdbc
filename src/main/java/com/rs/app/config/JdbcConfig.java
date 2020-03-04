package com.rs.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rs.app.exception.translator.CustomSQLErrorCodeSQLExceptionTranslator;

@Configuration
public class JdbcConfig {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private CustomSQLErrorCodeSQLExceptionTranslator exceptionTranslator;

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setExceptionTranslator(exceptionTranslator);
		return jdbcTemplate;
	}
}
