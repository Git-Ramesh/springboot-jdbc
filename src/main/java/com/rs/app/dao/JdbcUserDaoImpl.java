package com.rs.app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rs.app.bo.JdbcUserBo;
import com.rs.app.exception.translator.CustomSQLErrorCodeSQLExceptionTranslator;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class JdbcUserDaoImpl implements JdbcUserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public JdbcUserBo save(JdbcUserBo jdbcUserBo) {
		System.out.println(jdbcTemplate.getExceptionTranslator().getClass().getCanonicalName());
		System.out.println(CustomSQLErrorCodeSQLExceptionTranslator.class
				.isAssignableFrom(jdbcTemplate.getExceptionTranslator().getClass()));
		System.out.println(jdbcTemplate.getExceptionTranslator() instanceof CustomSQLErrorCodeSQLExceptionTranslator);
		final String sql = "INSERT INTO JDBC_USER (NAME, EMAIL) VALUES (?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		// PreparedStatementCreator
		jdbcTemplate.update(conn -> {
			PreparedStatement stmt = conn.prepareStatement(sql, new String[] { "id" });
			stmt.setString(1, jdbcUserBo.getName());
			stmt.setString(2, jdbcUserBo.getEmail());
			return stmt;
		}, keyHolder);
		jdbcUserBo.setId(keyHolder.getKey().longValue());
		sleep(10, TimeUnit.SECONDS);
		throwException();
		return jdbcUserBo;
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public JdbcUserBo get(long id) {
		final String sql = "SELECT ID, NAME, EMAIL FROM JDBC_USER WHERE ID=?";
		return jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNum) -> {
			JdbcUserBo jdbcUserBo = new JdbcUserBo(rs.getLong("id"), rs.getString("name"), rs.getString("email"));
			sleep(5, TimeUnit.SECONDS);
			return jdbcUserBo;
		});
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public List<JdbcUserBo> getAll() {
		final String sql = "SELECT ID, NAME, EMAIL FROM JDBC_USER";
		final List<JdbcUserBo> jdbcUsers = new ArrayList<>();
		// RowCallbackHandler
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				JdbcUserBo jdbcUserBo = new JdbcUserBo();
				jdbcUserBo.setId(rs.getLong("id"));
				jdbcUserBo.setName(rs.getString("name"));
				jdbcUserBo.setEmail(rs.getString("email"));
				jdbcUsers.add(jdbcUserBo);
			}
		});
		return jdbcUsers;
	}

	private void sleep(long timeout, TimeUnit timeUnit) {
		try {
			timeUnit.sleep(timeout);
		} catch (InterruptedException e) {
			// Ignore...
		}
	}

	private void throwException() {
		throw new RuntimeException("Oh No......");
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public JdbcUserBo update(JdbcUserBo jdbcUserBo) {
		final String sql = "UPDATE JDBC_USER SET EMAIL=? WHERE ID=?";
		jdbcTemplate.update(sql, jdbcUserBo.getEmail(), jdbcUserBo.getId());
		sleep(10, TimeUnit.SECONDS);
		return jdbcUserBo;
	}
}
