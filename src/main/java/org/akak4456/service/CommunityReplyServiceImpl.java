package org.akak4456.service;

import org.akak4456.domain.CommunityReply;
import org.akak4456.persistence.CommunityReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CommunityReplyServiceImpl implements ReplyService<CommunityReply> {
	@Autowired
	private CommunityReplyRepository replyRepo;
	@Override
	public boolean addReply(CommunityReply reply) {
		// TODO Auto-generated method stub
		replyRepo.save(reply);
		return true;
	}

	@Override
	public boolean updateReply(CommunityReply reply) {
		// TODO Auto-generated method stub
		replyRepo.save(reply);
		return true;
	}

	@Override
	public Page<CommunityReply> getListWithPaging(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
		//return replyRepo.findAll(replyRepo.makePredicate(),pageable);
	}

	@Override
	public boolean deleteReply(Long rno) {
		// TODO Auto-generated method stub
		CommunityReply newReply = replyRepo.findById(rno).get();
		newReply.setReply("삭제된 내용");
		replyRepo.save(newReply);
		return true;
	}

}
