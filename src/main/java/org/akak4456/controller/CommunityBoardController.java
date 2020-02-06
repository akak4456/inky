package org.akak4456.controller;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.service.CommunityBoardService;
import org.akak4456.vo.BoardForm;
import org.akak4456.vo.PageMaker;
import org.akak4456.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.java.Log;

@RestController
@Log
@RequestMapping("/community/**")
public class CommunityBoardController {
	@Autowired
	private CommunityBoardService communityBoardService;
	@GetMapping("/list") 
	public ModelAndView method2(PageVO pageVO) { 
		Page<CommunityBoard> boards = communityBoardService.getListWithPaging(null, null, 
				pageVO.makePageable(0, "bno"));
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.addObject("result", new PageMaker<CommunityBoard>(boards));
		modelAndView.setViewName("/community/community.html"); 
		return modelAndView; 
	}
	@GetMapping("/getOne/{bno}")
	public ModelAndView getBoard(@PathVariable("bno")Long bno,PageVO pageVO) {
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.addObject("board",communityBoardService.getOne(bno));
		modelAndView.setViewName("/community/communityOneBoard.html");
		return modelAndView;
	}
	@GetMapping("/write") 
	public ModelAndView writeget() { 
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.setViewName("/community/communitywrite.html"); 
		return modelAndView; 
	}
	@PostMapping("/write")
	public ResponseEntity<String> addBoard(@RequestBody BoardForm boardForm){
		log.info("addBoard..."+boardForm);
		communityBoardService.save(boardForm);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
}
