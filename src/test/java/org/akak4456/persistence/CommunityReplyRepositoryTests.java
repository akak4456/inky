package org.akak4456.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.domain.CommunityReply;
import org.akak4456.vo.PageVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class CommunityReplyRepositoryTests {
	@Autowired
	private CommunityReplyRepository repo;

	//@Test
	public void generateReply() {
		CommunityBoard board = new CommunityBoard();
		board.setBno(2L);
		// 메인 1000개 댓글 만들기
		IntStream.range(0, 1000).forEach(i -> {
			CommunityReply reply = new CommunityReply();
			//reply.setParent_rno(-1L);
			reply.setBoard(board);
			reply.setReplier("user" + i);
			reply.setReply("댓글" + i);
			repo.save(reply);
		});
		for (Long i = 1L; i <= 100L; i++) {
			CommunityReply parentReply = repo.findById(i).get();
			List<CommunityReply> childReply = new ArrayList<>();
			IntStream.range(0, 10).forEach(t -> {
				CommunityReply reply = new CommunityReply();
				reply.setBoard(board);
				reply.setReplier("user" + t);
				reply.setReply("대댓글" + t);
				reply.setParent(parentReply);
				repo.save(reply);
				childReply.add(reply);
			});
			parentReply.setChildren(childReply);
			repo.save(parentReply);
		}
	}
	@Test
	public void testPaging() {
		PageVO pageVO = new PageVO();
		pageVO.setSize(20);
		Page<CommunityReply> ret = repo.getListWithPaging(pageVO.makePageable(1, "rno"));
		ret.getContent().forEach(r->log.info(r.getRno()+" "+r.getReply()));
	}
}
