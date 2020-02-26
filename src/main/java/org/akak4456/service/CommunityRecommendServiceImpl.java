package org.akak4456.service;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.domain.CommunityRecommend;
import org.akak4456.domain.Recommend;
import org.akak4456.domain.RecommendId;
import org.springframework.stereotype.Service;
@Service
public class CommunityRecommendServiceImpl extends RecommendService<CommunityBoard,CommunityRecommend>{

	@Override
	public Recommend getRecommend(RecommendId id) {
		// TODO Auto-generated method stub
		CommunityRecommend recommend = new CommunityRecommend();
		recommend.setId(id);
		return recommend;
	}
}
