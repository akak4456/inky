package org.akak4456.controller;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.service.CommunityBoardService;
import org.akak4456.vo.PageMaker;
import org.akak4456.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		modelAndView.addObject("pageVO",pageVO);
		modelAndView.addObject("boardkindKO","커뮤니티");
		modelAndView.addObject("boardkindEN","community");
		modelAndView.addObject("result", new PageMaker<CommunityBoard>(boards));
		modelAndView.setViewName("/board/list.html"); 
		return modelAndView; 
	}
	@GetMapping("/getOne/{bno}")
	public ModelAndView getBoard(@PathVariable("bno")Long bno,PageVO pageVO) {
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.addObject("board",communityBoardService.getOne(bno));
		modelAndView.addObject("pageVO", pageVO);
		modelAndView.addObject("boardkindKO","커뮤니티");
		modelAndView.addObject("boardkindEN","community");
		modelAndView.setViewName("/board/oneBoard.html");
		return modelAndView;
	}
	@GetMapping("/modify/{bno}")
	public ModelAndView modifyBoard(@PathVariable("bno")Long bno,PageVO pageVO) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("board",communityBoardService.getOne(bno));
		modelAndView.addObject("pageVO",pageVO);
		modelAndView.addObject("boardkindKO","커뮤니티");
		modelAndView.addObject("boardkindEN","community");
		modelAndView.setViewName("/board/modify");
		return modelAndView;
	}
	@GetMapping("/write") 
	public ModelAndView writeget(PageVO pageVO) { 
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.addObject("pageVO",pageVO);
		modelAndView.addObject("boardkindKO","커뮤니티");
		modelAndView.addObject("boardkindEN","community");
		modelAndView.setViewName("/board/write.html"); 
		return modelAndView; 
	}
	@PostMapping("/write")
	public ResponseEntity<String> addBoard(@RequestBody CommunityBoard board){
		log.info("addBoard..."+board);
		communityBoardService.save(board);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	@PutMapping("/modify/{bno}")
	public ResponseEntity<String> modifyBoard(@RequestBody CommunityBoard board){
		log.info("modifyBoard..."+board);
		communityBoardService.update(board);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	@DeleteMapping("/delete/{bno}")
	public ResponseEntity<String> deleteBoard(@PathVariable("bno") Long bno){
		log.info("deleteBoard..."+bno);
		if(communityBoardService.deleteBoard(bno))
			return new ResponseEntity<>("success",HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
