package org.akak4456.persistence;

import org.akak4456.domain.Reply;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.types.Predicate;
@NoRepositoryBean
public interface ReplyRepository <T extends Reply> extends CrudRepository<T, Long>,QuerydslPredicateExecutor<T>{
	public Predicate makePredicate(Long bno);
}
