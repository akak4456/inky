package org.akak4456.service;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.vo.BoardForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityBoardService {
	public boolean save(BoardForm boardForm);
	public Page<CommunityBoard> getListWithPaging(String type, String keyword, Pageable pageable);
	public CommunityBoard getOne(Long bno);
	public boolean update(BoardForm boardForm);
	public boolean deleteBoard(Long bno);
}
