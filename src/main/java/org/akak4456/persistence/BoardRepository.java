package org.akak4456.persistence;

import org.akak4456.domain.Board;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository<T extends Board> extends CrudRepository<T, Long>,QuerydslPredicateExecutor<T> {

}
