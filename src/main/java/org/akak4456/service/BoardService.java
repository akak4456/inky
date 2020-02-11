package org.akak4456.service;

import org.akak4456.domain.CommunityBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService<T> {
	public boolean save(T board);
	public Page<T> getListWithPaging(String type, String keyword, Pageable pageable);
	public T getOne(Long bno);
	public boolean update(T board);
	public boolean deleteBoard(Long bno);
}
