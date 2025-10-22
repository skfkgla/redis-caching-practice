package com.example.redis.session.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class SessionController {
	@GetMapping("/set")
	public String set(@RequestParam("q") String q, HttpSession session) {
		session.setAttribute("q", q);
		return "Saved: " + q;
	}

	@GetMapping("/get")
	public String get(HttpSession session) {
		return session.getAttribute("q").toString();
	}
}