package org.akak4456.controller;

import org.akak4456.vo.PageVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/login")
	public void login() {

	}
	
	@GetMapping("/accessDenied")
	public void accessDenied() {

	}
	
	@GetMapping("/logout")
	public void logout() {

	}
}
