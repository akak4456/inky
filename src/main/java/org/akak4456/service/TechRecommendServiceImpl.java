package org.akak4456.service;

import org.akak4456.domain.CommunityRecommend;
import org.akak4456.domain.Recommend;
import org.akak4456.domain.RecommendId;
import org.akak4456.domain.TechBoard;
import org.akak4456.domain.TechRecommend;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
@Service
@Log
public class TechRecommendServiceImpl extends RecommendService<TechBoard, TechRecommend> {

	@Override
	public Recommend getRecommend(RecommendId id) {
		// TODO Auto-generated method stub
		TechRecommend recommend = new TechRecommend();
		recommend.setId(id);
		return recommend;
	}

}
