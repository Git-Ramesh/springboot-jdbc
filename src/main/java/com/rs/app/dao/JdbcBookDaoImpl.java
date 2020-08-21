package com.rs.app.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.rs.app.bo.JdbcBookBo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class JdbcBookDaoImpl implements JdbcBookDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public JdbcBookBo save(JdbcBookBo jdbcBookBo) {
		log.debug(jdbcBookBo.toString());
		final String q = "INSERT INTO JDBC_BOOK(TITLE, AUTHOR, PRICE, PUBLISHER) VALUES (?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			PreparedStatement pstmt = con.prepareStatement(q, new String[] { "id" });
			pstmt.setString(1, jdbcBookBo.getTitle());
			pstmt.setString(2, jdbcBookBo.getAuthor());
			pstmt.setFloat(3, jdbcBookBo.getPrice());
			pstmt.setString(4, jdbcBookBo.getPublisher());
			return pstmt;
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		jdbcBookBo.setId(id);
		return jdbcBookBo;
	}

	@Override
	public List<JdbcBookBo> findAll() {
		final String q = "SELECT ID, TITLE, AUTHOR, PRICE, PUBLISHER FROM JDBC_BOOK";
		List<JdbcBookBo> books = new ArrayList<>();
		jdbcTemplate.query(q, (RowCallbackHandler) rs -> {
			while (rs.next()) {
				JdbcBookBo book = new JdbcBookBo();
				book.setId(rs.getLong(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setPrice(rs.getFloat(4));
				book.setPublisher(rs.getString(5));
				books.add(book);
			}
		});
		return books;
	}

	@Override
	public Optional<JdbcBookBo> findById(long id) {
		final String q = "SELECT ID, TITLE, AUTHOR, PRICE, PUBLISHER FROM JDBC_BOOK WHERE ID=?";

		try {
			return jdbcTemplate.queryForObject(q, new Object[] { id }, (rs, rowNum) -> {
				JdbcBookBo book = new JdbcBookBo();
				book.setId(rs.getLong(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setPrice(rs.getFloat(4));
				book.setPublisher(rs.getString(5));
				return Optional.of(book);
			});
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

}
