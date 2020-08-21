package com.rs.app.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rs.app.bo.JdbcBookBo;
import com.rs.app.dao.JdbcBookDao;
import com.rs.app.dto.JdbcBookDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JdbcBookServiceHelper {
	@Autowired
	private JdbcBookDao jdbcBookDao;

//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Transactional(propagation = Propagation.NESTED)
	public JdbcBookDto register(JdbcBookDto jdbcBookDto) {
		JdbcBookDto savedJdbcBookDto = null;
		
		try {
			JdbcBookBo jdbcBookBo = new JdbcBookBo();
			BeanUtils.copyProperties(jdbcBookDto, jdbcBookBo);
			JdbcBookBo savedJdbcBookBo = jdbcBookDao.save(jdbcBookBo);
			savedJdbcBookDto = new JdbcBookDto();
			BeanUtils.copyProperties(savedJdbcBookBo, savedJdbcBookDto);
			if (true) {
				throw new RuntimeException("Blah.....");
			}
		} catch (Exception e) {
			log.debug("Exception block");
			throw e;
		}
		return savedJdbcBookDto;
	}
}
