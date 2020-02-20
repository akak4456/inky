package org.akak4456.service;

import javax.transaction.Transactional;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.domain.CommunityReply;
import org.akak4456.persistence.CommunityBoardRepository;
import org.akak4456.persistence.CommunityReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
@Service
@Log
public class CommunityReplyServiceImpl implements ReplyService<CommunityReply> {
	@Autowired
	private CommunityBoardRepository boardRepo;
	@Autowired
	private CommunityReplyRepository replyRepo;
	@Transactional
	@Override
	public boolean addReply(CommunityReply reply) {
		// TODO Auto-generated method stub
		CommunityBoard board = boardRepo.findById(reply.getBoard().getBno()).get();
		board.setReplycnt(board.getReplycnt()+1);
		boardRepo.save(board);
		//board reply 수 증가
		replyRepo.save(reply);
		return true;
	}
	@Transactional
	@Override
	public boolean updateReply(CommunityReply reply) {
		// TODO Auto-generated method stub
		CommunityReply newReply = replyRepo.findById(reply.getRno()).get();
		newReply.setReply(reply.getReply());
		replyRepo.save(newReply);
		return true;
	}
	@Transactional
	@Override
	public Page<CommunityReply> getListWithPaging(Long bno,Pageable pageable) {
		// TODO Auto-generated method stub
		return replyRepo.findAll(replyRepo.makePredicate(bno), pageable);
		//return replyRepo.findAll(replyRepo.makePredicate(),pageable);
	}
	@Transactional
	@Override
	public boolean deleteReply(Long rno) {
		// TODO Auto-generated method stub
		CommunityReply newReply = replyRepo.findById(rno).get();
		
		CommunityBoard board = boardRepo.findById(newReply.getBoard().getBno()).get();
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
