package org.akak4456.persistence;

import org.akak4456.domain.Reply;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ReplyRepository <T extends Reply> extends CrudRepository<T, Long>,QuerydslPredicateExecutor<T>{

}
