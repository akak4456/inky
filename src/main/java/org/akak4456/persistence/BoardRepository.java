package org.akak4456.persistence;

import org.akak4456.domain.Board;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.types.Predicate;
@NoRepositoryBean
public interface BoardRepository<T extends Board> extends CrudRepository<T, Long>,QuerydslPredicateExecutor<T> {
	public Predicate makePredicate(String type,String keyword);
}
