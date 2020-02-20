package org.akak4456.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.persistence.CommunityBoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class CommunityRecommendTests {
	@Autowired
	private CommunityBoardRepository boardRepo;
	@Autowired
	@Qualifier("communityRecommendServiceImpl")
	private RecommendService service;
	
	//@Test
	public void init() {
		boardRepo.deleteAll();
		assertThat(boardRepo.count(),is(0L));
		CommunityBoard board = new CommunityBoard();
		board.setContent("내용");
		board.setUserid("작성자");
		board.setTitle("제목");
		boardRepo.save(board);
		assertThat(boardRepo.count(),is(1L));
	}
	@Test
	public void test1() {
		List<CommunityBoard> boards = new ArrayList<>();
		boardRepo.findAll().forEach(board->boards.add(board));
		assertThat(boards.size(),is(1));
		CommunityBoard board = boards.get(0);
		assertThat(board.getRecommendcnt(),is(0));
		service.upRecommendcnt("user00", board.getBno());
		board = boardRepo.findById(board.getBno()).get();
		assertThat(board.getRecommendcnt(),is(1));
		service.upRecommendcnt("user00", board.getBno());
		board = boardRepo.findById(board.getBno()).get();
		assertThat(board.getRecommendcnt(),is(1));
		service.downRecommendcnt("user01", board.getBno());
		board = boardRepo.findById(board.getBno()).get();
		assertThat(board.getRecommendcnt(),is(0));
		service.downRecommendcnt("user01", board.getBno());
		board = boardRepo.findById(board.getBno()).get();
		assertThat(board.getRecommendcnt(),is(0));
	}
}
