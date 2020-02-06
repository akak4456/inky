package org.akak4456.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.persistence.CommunityBoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class CommunityPageTest {
	@Autowired
	private CommunityBoardRepository repo;
	
	//@Test
	public void makeDummy() {
		List<CommunityBoard> boards = new ArrayList<>();
		IntStream.range(0,1000).forEach(i->{
			CommunityBoard board = new CommunityBoard();
			board.setContent("<p>내용"+i+"</p>");
			board.setTitle("제목"+i);
			board.setUserid("user"+i);
			boards.add(board);
		});
		repo.saveAll(boards);
	}
	
	//@Test
	public void testPaging() {
		Pageable pageable = PageRequest.of(0, 10,Direction.DESC,"bno");
		Page<CommunityBoard> result = repo.findAll(repo.makePredicate(null, null),pageable);
		
		result.getContent().forEach(board->log.info(""+board.getTitle()));
	}
	//@Test
	public void testPaging2() {
		Pageable pageable = PageRequest.of(0, 10,Direction.DESC,"bno");
		Page<CommunityBoard> result = repo.findAll(repo.makePredicate("T", "10"),pageable);
		
		result.getContent().forEach(board->log.info(""+board.getTitle()));
	}
	@Test
	public void testPaging3() {
		Pageable pageable = PageRequest.of(0, 10,Direction.DESC,"bno");
		Page<CommunityBoard> result = repo.findAll(repo.makePredicate("TC", "10"),pageable);
		
		result.getContent().forEach(board->log.info(""+board.getTitle()));
	}
}
