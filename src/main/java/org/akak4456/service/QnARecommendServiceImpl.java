package org.akak4456.service;

import org.akak4456.domain.CommunityRecommend;
import org.akak4456.domain.QnABoard;
import org.akak4456.domain.QnARecommend;
import org.akak4456.domain.Recommend;
import org.akak4456.domain.RecommendId;
import org.springframework.stereotype.Service;

@Service
public class QnARecommendServiceImpl extends RecommendService<QnABoard, QnARecommend> {
	@Override
	public Recommend getRecommend(RecommendId id) {
		// TODO Auto-generated method stub
		QnARecommend recommend = new QnARecommend();
		recommend.setId(id);
		return recommend;
	}
}
