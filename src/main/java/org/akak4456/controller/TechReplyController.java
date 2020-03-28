package org.akak4456.controller;

import org.akak4456.constant.Constants;
import org.akak4456.domain.CommunityBoard;
import org.akak4456.domain.CommunityReply;
import org.akak4456.domain.TechBoard;
import org.akak4456.domain.TechReply;
import org.akak4456.service.ReplyService;
import org.akak4456.vo.PageMaker;
import org.akak4456.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@RestController
@Log
@RequestMapping("/tech/reply/**")
public class TechReplyController {
	@Autowired
	@Qualifier("techReplyServiceImpl")
	private ReplyService replyService;
	@GetMapping("/list/{bno}")
	public ResponseEntity<PageMaker<TechReply>> getList(PageVO pageVO,@PathVariable("bno") Long bno){
		Page<TechReply> result = null;
		if(pageVO.getPage() == 0) {
			//마지막 페이지를 요구하면
			pageVO.setPage(1);
			result = replyService.getListWithPaging(bno, pageVO.makePageable(1, "path"));
			int lastPage = result.getTotalPages();
			pageVO.setPage(lastPage);
		}
		result = replyService.getListWithPaging(bno, pageVO.makePageable(1, "path"));
		PageMaker<TechReply> replies = new PageMaker<TechReply>(result);
		return new ResponseEntity<>(replies,HttpStatus.OK);
	}
	@Secured({"ROLE_BASIC","ROLE_ADMIN"})
	@PostMapping("/write/{bno}")
	public ResponseEntity<String> write(@RequestBody TechReply reply,@PathVariable("bno")Long bno){
		TechBoard board = new TechBoard();
		board.setBno(bno);
		reply.setBoard(board);
		replyService.addReply(reply);
		return new ResponseEntity<>(Constants.REPLY_ADD_SUCCESS,HttpStatus.OK);
	}
	@PreAuthorize("#userid == authentication.principal.member.uid")
	@DeleteMapping("/delete/{rno}")
	public ResponseEntity<String> deleteReply(@PathVariable("rno") Long rno,String userid){
		replyService.deleteReply(rno);
		return new ResponseEntity<>(Constants.REPLY_DELETE_SUCCESS,HttpStatus.OK);
	}
	@PreAuthorize("#reply.replier == authentication.principal.member.uid")
	@PutMapping("/modify/{bno}")
	public ResponseEntity<String> modifyReply(@PathVariable("bno") Long bno, @RequestBody TechReply reply){
		TechBoard board = new TechBoard();
		board.setBno(bno);
		reply.setBoard(board);
		replyService.updateReply(reply);
		return new ResponseEntity<>(Constants.REPLY_MODIFY_SUCCESS,HttpStatus.OK);
	}
}
