package org.akak4456.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.java.Log;

@Controller
@Log
public class IndexController {
	@GetMapping("/")
	public String getIndex() {
		return "/index";
	}
}
