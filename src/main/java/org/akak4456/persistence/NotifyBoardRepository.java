package org.akak4456.persistence;

import org.akak4456.domain.NotifyBoard;
import org.akak4456.domain.QCommunityReply;
import org.akak4456.domain.QNotifyBoard;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface NotifyBoardRepository extends CrudRepository<NotifyBoard, Long>,QuerydslPredicateExecutor<NotifyBoard> {
	public default Predicate makePredicate() {
		BooleanBuilder builder = new BooleanBuilder();
		
		QNotifyBoard board = QNotifyBoard.notifyBoard;
		
		builder.and(board.bno.gt(0));
		return builder;
	}
}
	