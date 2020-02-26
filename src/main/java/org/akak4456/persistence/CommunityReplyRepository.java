package org.akak4456.persistence;

import org.akak4456.domain.CommunityReply;
import org.akak4456.domain.QCommunityReply;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface CommunityReplyRepository extends ReplyRepository<CommunityReply>{
	@Override
	public default Predicate makePredicate(Long bno) {
		BooleanBuilder builder = new BooleanBuilder();
		
		QCommunityReply reply = QCommunityReply.communityReply;
		
		builder.and(reply.rno.gt(0));
		builder.and(reply.board.bno.eq(bno));
		return builder;
	} 
}
