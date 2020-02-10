package org.akak4456.service;

import org.akak4456.domain.CommunityReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityReplyService {
	public boolean addReply(CommunityReply reply);
	public boolean updateReply(CommunityReply reply);
	public Page<CommunityReply> getListWithPaging(Pageable pageable);
	public boolean deleteReply(Long rno);
}
