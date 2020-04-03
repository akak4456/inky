package org.akak4456.persistence;

import org.akak4456.domain.QQnAReply;
import org.akak4456.domain.QnAReply;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface QnAReplyRepository extends ReplyRepository<QnAReply> {
	@Override
	public default Predicate makePredicate(Long bno) {
		BooleanBuilder builder = new BooleanBuilder();
		
		QQnAReply reply = QQnAReply.qnAReply;
		
		builder.and(reply.rno.gt(0));
		builder.and(reply.board.bno.eq(bno));
		return builder;
	} 
}
