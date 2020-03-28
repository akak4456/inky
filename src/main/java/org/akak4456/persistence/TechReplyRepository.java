package org.akak4456.persistence;

import org.akak4456.domain.QTechReply;
import org.akak4456.domain.TechReply;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface TechReplyRepository extends ReplyRepository<TechReply> {
	@Override
	public default Predicate makePredicate(Long bno) {
		BooleanBuilder builder = new BooleanBuilder();
		
		QTechReply reply = QTechReply.techReply;
		
		builder.and(reply.rno.gt(0));
		builder.and(reply.board.bno.eq(bno));
		return builder;
	} 
}
