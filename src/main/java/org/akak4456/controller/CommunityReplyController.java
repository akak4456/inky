package org.akak4456.controller;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.domain.CommunityReply;
import org.akak4456.service.ReplyService;
import org.akak4456.vo.PageMaker;
import org.akak4456.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@RestController
@Log
@RequestMapping("/community/reply/**")
public class CommunityReplyController {
	@Autowired
	@Qualifier("communityReplyServiceImpl")
	private ReplyService replyService;
	@GetMapping("/list/{bno}")
	public ResponseEntity<PageMaker<CommunityReply>> getList(PageVO pageVO,@PathVariable("bno") Long bno){
		log.info("getListReply..."+pageVO.getPage());
		Page<CommunityReply> result = null;
		if(pageVO.getPage() == 0) {
			//마지막 페이지를 요구하면
			pageVO.setPage(1);
			result = replyService.getListWithPaging(bno, pageVO.makePageable(1, "path"));
			int lastPage = result.getTotalPages();
			pageVO.setPage(lastPage);
		}
		result = replyService.getListWithPaging(bno, pageVO.makePageable(1, "path"));
		PageMaker<CommunityReply> replies = new PageMaker<CommunityReply>(result);
		return new ResponseEntity<>(replies,HttpStatus.OK);
	}
	@PostMapping("/write/{bno}")
	public ResponseEntity<PageMaker<CommunityReply>> write(@RequestBody CommunityReply reply,@PathVariable("bno")Long bno){
		log.info("addReply..."+bno);
		CommunityBoard board = new CommunityBoard();
		board.setBno(bno);
		reply.setBoard(board);
		replyService.addReply(reply);
		//마지막 페이지로 돌아간다
		Page<CommunityReply> result = null;
		PageVO pageVO = new PageVO();
		pageVO.setPage(1);
		result = replyService.getListWithPaging(bno, pageVO.makePageable(1, "path"));
		int lastPage = result.getTotalPages();
		log.info("LAST PAGE..."+lastPage);
		pageVO.setPage(lastPage);
		result = replyService.getListWithPaging(bno, pageVO.makePageable(1, "path"));
		result.getContent().forEach(r->log.info(r.getReply()+""));
		PageMaker<CommunityReply> replies = new PageMaker<CommunityReply>(result);
		return new ResponseEntity<>(replies,HttpStatus.OK);
	}
}
