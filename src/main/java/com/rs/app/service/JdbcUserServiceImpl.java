package com.rs.app.service;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.rs.app.bo.JdbcUserBo;
import com.rs.app.dao.JdbcUserDao;
import com.rs.app.dto.JdbcUserDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JdbcUserServiceImpl implements JdbcUserService {
	private final String tmpdir = System.getProperty("java.io.tmpdir");
	private final Random rand = new SecureRandom();
	@Autowired
	private JdbcUserDao jdbcUserDao;

	@Override
	public JdbcUserDto addJdbcUser(JdbcUserDto jdbcUserDto) {
		JdbcUserBo jdbcUserBo = new JdbcUserBo();
		BeanUtils.copyProperties(jdbcUserDto, jdbcUserBo);
		JdbcUserBo savedJdbcUserBo = jdbcUserDao.save(jdbcUserBo);
		jdbcUserDto.setId(savedJdbcUserBo.getId());
		return jdbcUserDto;
	}

	@Override
	public JdbcUserDto getJdbcUser(long id) {
		JdbcUserBo jdbcUserBo = jdbcUserDao.get(id);
		JdbcUserDto jdbcUserDto = new JdbcUserDto();
		BeanUtils.copyProperties(jdbcUserBo, jdbcUserDto);
		return jdbcUserDto;
	}

	@Override
	public List<JdbcUserDto> getAllJdbcUsers() {
		List<JdbcUserBo> all = jdbcUserDao.getAll();
		// @formatter:off
		return all.parallelStream()
			.map(jdbcUserBo -> {
				 JdbcUserDto jdbcUserDto = new JdbcUserDto();
				BeanUtils.copyProperties(jdbcUserBo,jdbcUserDto);
				return jdbcUserDto;
			})
			.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public JdbcUserDto updateJdbcUser(JdbcUserDto jdbcUserDto) {
		JdbcUserBo jdbcUserBo = new JdbcUserBo();
		BeanUtils.copyProperties(jdbcUserDto, jdbcUserBo);
		jdbcUserDao.update(jdbcUserBo);
		return jdbcUserDto;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@Override
	public void transactionTest(long id) throws IOException {
		// Read JdbcUser
		JdbcUserBo jdbcUserBo = jdbcUserDao.get(id);
		log.info("Got the user");
		File file = createFileInTempLocation();
		log.info(file.getAbsolutePath());
		jdbcUserBo.setEmail("email_new@test.com");
		log.info("Updating jdbcuser email");
		jdbcUserDao.update(jdbcUserBo);
	}

	private File createFileInTempLocation() throws IOException {
		String fileName = generateFileName(10);
		File file = new File(tmpdir + File.separator + fileName);
		if(!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	private String generateFileName(int fileNameLength) {
		final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder fileNameBuilder = new StringBuilder();
		for (int i = 0; i < fileNameLength; i++) {
			int index = rand.nextInt(characters.length());
			fileNameBuilder.append(characters.charAt(index));
		}
		return fileNameBuilder.toString();
	}

}
