package org.akak4456.persistence;

import java.util.ArrayList;
import java.util.List;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.domain.QCommunityBoard;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface CommunityBoardRepository extends BoardRepository<CommunityBoard> {
	public default Predicate makePredicate(String type,String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		
		QCommunityBoard board = QCommunityBoard.communityBoard;
		
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
