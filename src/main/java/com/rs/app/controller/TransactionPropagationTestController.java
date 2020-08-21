package com.rs.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rs.app.dto.JdbcBookDto;
import com.rs.app.service.JdbcBookService;

@RestController
@RequestMapping("/transaction-propagation-test")
public class TransactionPropagationTestController {
	@Autowired
	private JdbcBookService jdbcBookService;

	@GetMapping
	public void sample() {
		JdbcBookDto o1 = new JdbcBookDto(0l, "Hiberate", "Ramesh", 7.36f, "RadiantSage");
		JdbcBookDto o2 = new JdbcBookDto(0l, "ES6 For Humans", "xxxxx", 8.36f, "RadiantSage");
		jdbcBookService.registerBooks(o1, o2);
	}
}
