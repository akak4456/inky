package org.akak4456.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.java.Log;

@RestController
@Log
@RequestMapping("/articles/*")
public class ArticleController {
	@GetMapping("/community")
	public ModelAndView welcome() {
		//return articles/community.html
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("/articles/community");
	    return modelAndView;
	}
}
