package org.akak4456.persistence;

import org.akak4456.domain.CommunityReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityReplyRepositoryCustom {
	Page<CommunityReply> getListWithPaging(Pageable page);
}
