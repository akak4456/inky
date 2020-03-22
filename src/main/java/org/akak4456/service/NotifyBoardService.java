package org.akak4456.service;

import org.akak4456.domain.NotifyBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotifyBoardService {
	public void save(NotifyBoard board);
	public Page<NotifyBoard> getList(Pageable pageable);
	public NotifyBoard getOne(Long bno);
}
