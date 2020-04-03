package org.akak4456.persistence;

import org.akak4456.domain.QCommunityBoard;
import org.akak4456.domain.QQnABoard;
import org.akak4456.domain.QnABoard;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface QnABoardRepository extends BoardRepository<QnABoard> {
	@Override
	public default Predicate makePredicate(String type,String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		
		QQnABoard board = QQnABoard.qnABoard;
		
		builder.and(board.bno.gt(0));
		
		if(type == null) {
			return builder;
		}
		Predicate[] predicates = new Predicate[type.length()];
		for(int i=0;i<type.length();i++) {
			if(type.charAt(i) == 'T') {
				predicates[i] = board.title.like("%"+keyword+"%");
			}
			else if(type.charAt(i) == 'C') {
				predicates[i] = board.content.like("%"+keyword+"%");
			}
			else if(type.charAt(i) == 'W') {
				predicates[i] = board.userid.like("%"+keyword+"%");
			}
		}
		builder.andAnyOf(predicates);
		return builder;
	}
}
