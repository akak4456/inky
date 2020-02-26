package org.akak4456.service;

import javax.transaction.Transactional;

import org.akak4456.domain.Board;
import org.akak4456.domain.CommunityBoard;
import org.akak4456.domain.CommunityReply;
import org.akak4456.domain.Reply;
import org.akak4456.persistence.BoardRepository;
import org.akak4456.persistence.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ReplyService<B extends Board,R extends Reply> {
	@Autowired
	private BoardRepository<B> boardRepo;
	@Autowired
	private ReplyRepository<R> replyRepo;
	@Transactional
	public boolean addReply(R reply) {
		// TODO Auto-generated method stub
		B board = boardRepo.findById(reply.getBoard().getBno()).get();
		board.setReplycnt(board.getReplycnt()+1);
		boardRepo.save(board);
		//board reply 수 증가
		replyRepo.save(reply);
		return true;
	}
	@Transactional
	public boolean updateReply(R reply) {
		// TODO Auto-generated method stub
		R newReply = replyRepo.findById(reply.getRno()).get();
		newReply.setReply(reply.getReply());
		replyRepo.save(newReply);
		return true;
	}
	@Transactional
	public Page<R> getListWithPaging(Long bno,Pageable pageable) {
		// TODO Auto-generated method stub
		return replyRepo.findAll(replyRepo.makePredicate(bno), pageable);
		//return replyRepo.findAll(replyRepo.makePredicate(),pageable);
	}
	@Transactional
	public boolean deleteReply(Long rno) {
		// TODO Auto-generated method stub
		R newReply = replyRepo.findById(rno).get();
		B board = boardRepo.findById(newReply.getBoard().getBno()).get();
		board.setReplycnt(board.getReplycnt()-1);
		boardRepo.save(board);
		//board reply 수 감소
		newReply.setReplier("삭제된 작성자");
		newReply.setReply("삭제된 내용");
		newReply.setIsdelete('Y');
		replyRepo.save(newReply);
		return true;
	}
}
