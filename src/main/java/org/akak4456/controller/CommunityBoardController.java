package org.akak4456.controller;

import javax.annotation.security.PermitAll;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.persistence.CommunityBoardRepository;
import org.akak4456.vo.BoardForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@RestController
@Log
@RequestMapping("/articles/**")
public class CommunityBoardController {
	@Autowired
	private CommunityBoardRepository repo;

	@PostMapping("/communitywrite")
	public ResponseEntity<String> addBoard(@RequestBody BoardForm boardForm){
		log.info("addBoard..."+boardForm);
		CommunityBoard board = new CommunityBoard();
		board.setUserid(boardForm.getUserid());
		board.setTitle(boardForm.getContent());
		board.setContent(boardForm.getContent());
		repo.save(board);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
}
