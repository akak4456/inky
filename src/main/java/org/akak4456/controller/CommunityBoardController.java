package org.akak4456.controller;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.service.BoardService;
import org.akak4456.service.RecommendService;
import org.akak4456.vo.PageMaker;
import org.akak4456.vo.PageVO;
import org.akak4456.vo.RecommendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/community/**")
public class CommunityBoardController {
	@Autowired
	@Qualifier("communityBoardServiceImpl")
	private BoardService communityBoardService;
	@Autowired
	@Qualifier("communityRecommendServiceImpl")
	private RecommendService recommendService;
	@GetMapping("/list") 
	public String method2(PageVO pageVO,Model model) {
		Pageable order = null;
		if(pageVO.getOrderBy() == 0) {
			//최신순
			order = pageVO.makePageable(0, "bno");
		}else if(pageVO.getOrderBy() == 1) {
			//추천순
			order = pageVO.makePageable(0, "recommendcnt");
		}else if(pageVO.getOrderBy() == 2) {
			//댓글순
			order = pageVO.makePageable(0, "replycnt");
		}else if(pageVO.getOrderBy() == 3) {
			//조회순
			order = pageVO.makePageable(0, "bno");
			//order = pageVO.makePageable(0, props)
		}
		Page<CommunityBoard> boards = communityBoardService.getListWithPaging(pageVO.getType(), pageVO.getKeyword(), 
				order);
		model.addAttribute("pageVO",pageVO);
		model.addAttribute("boardkindKO","커뮤니티");
		model.addAttribute("boardkindEN","community");
		model.addAttribute("result", new PageMaker<CommunityBoard>(boards));
		return "/board/list"; 
	}
	@GetMapping("/getOne/{bno}")
	public String getBoard(@PathVariable("bno")Long bno,PageVO pageVO,Model model) {
		model.addAttribute("board",communityBoardService.getOne(bno));
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("boardkindKO","커뮤니티");
		model.addAttribute("boardkindEN","community");
		return "/board/oneBoard";
	}
	@Secured({"ROLE_BASIC","ROLE_ADMIN"})
	@GetMapping("/modify/{bno}")
	public String modifyBoard(@PathVariable("bno")Long bno,PageVO pageVO,Model model) {
		model.addAttribute("board",communityBoardService.getOne(bno));
		model.addAttribute("pageVO",pageVO);
		model.addAttribute("boardkindKO","커뮤니티");
		model.addAttribute("boardkindEN","community");
		return "/board/modify";
	}
	@Secured({"ROLE_BASIC","ROLE_ADMIN"})
	@GetMapping("/write") 
	public String writeget(PageVO pageVO,Model model) { 
		model.addAttribute("pageVO",pageVO);
		model.addAttribute("boardkindKO","커뮤니티");
		model.addAttribute("boardkindEN","community");
		return "/board/write"; 
	}
	@Secured({"ROLE_BASIC","ROLE_ADMIN"})
	@PostMapping("/write")
	@ResponseBody
	public ResponseEntity<String> addBoard(@RequestBody CommunityBoard board){
		log.info("addBoard..."+board);
		communityBoardService.save(board);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	@PreAuthorize("#board.userid == authentication.principal.member.uid")
	@PutMapping("/modify/{bno}")
	@ResponseBody
	public ResponseEntity<String> modifyBoard(@RequestBody CommunityBoard board){
		log.info("modifyBoard..."+board);
		communityBoardService.update(board);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	@PreAuthorize("#userid == authentication.principal.member.uid")
	@DeleteMapping("/delete/{bno}")
	@ResponseBody
	public ResponseEntity<String> deleteBoard(@PathVariable("bno") Long bno,String userid){
		log.info("deleteBoard..."+bno);
		if(communityBoardService.deleteBoard(bno))
			return new ResponseEntity<>("success",HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@Secured({"ROLE_BASIC","ROLE_ADMIN"})
	@PostMapping("/recommend/{isupordown}")
	@ResponseBody
	public ResponseEntity<String> changeRecommend(@PathVariable("isupordown")String isupordown,@RequestBody RecommendVO recommend){
		log.info("change recommend..."+recommend);
		if(isupordown.equals("up")) {
			if(recommendService.upRecommendcnt(recommend.getUserid(), recommend.getBno())) {
				return new ResponseEntity<>("success",HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else if(isupordown.equals("down")) {
			if(recommendService.downRecommendcnt(recommend.getUserid(), recommend.getBno())) {
				return new ResponseEntity<>("success",HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return null;
	}
}
