package org.akak4456.service;

import org.akak4456.domain.CommunityReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyService<T> {
	public boolean addReply(T reply);
	public boolean updateReply(T reply);
	public Page<T> getListWithPaging(Long bno,Pageable pageable);
	public boolean deleteReply(Long rno);
}
