package com.rs.app.exception.translator;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomSQLErrorCodeSQLExceptionTranslator extends SQLErrorCodeSQLExceptionTranslator {

	@Override
	public DataAccessException customTranslate(String task, String sql, SQLException sqlEx) {
		log.info("=====================");
		log.info("Task: {}", task);
		log.info("SQL: {}", sql);
		log.info("Error Code: {}", sqlEx.getErrorCode());
		log.info("State: {}", sqlEx.getSQLState());
		log.info("=====================");
		if (sqlEx.getSQLState().equals("23505")) {
			return new DuplicateKeyException("Custom Exception translator - Integrity constraint violation.", sqlEx);
		}
		if(sqlEx.getSQLState().equals("40001")) {
			return new DuplicateKeyException("Custom Exception translator - could not serialize access due to concurrent update.", sqlEx);
		}
		return null;
	}
}
