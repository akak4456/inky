package org.akak4456.controller;

import org.akak4456.vo.BoardForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.java.Log;

@Controller
@Log
public class ArticleController {
	@GetMapping("/articles/community")
	public void welcome() {

	}
	@GetMapping("/articles/communitywrite")
	public void communitywrite() {
		
	}
}
