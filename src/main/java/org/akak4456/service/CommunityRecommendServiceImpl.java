package org.akak4456.service;

import javax.transaction.Transactional;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.domain.CommunityRecommend;
import org.akak4456.domain.RecommendId;
import org.akak4456.persistence.CommunityBoardRepository;
import org.akak4456.persistence.CommunityRecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CommunityRecommendServiceImpl implements RecommendService{
	@Autowired
	private CommunityBoardRepository boardRepo;
	@Autowired
	private CommunityRecommendRepository recommendRepo;
	@Transactional
	@Override
	public boolean upRecommendcnt(String userid, Long bno) {
		RecommendId id = new RecommendId(userid,bno);
		// TODO Auto-generated method stub
		if(recommendRepo.existsById(id)) {
			//이미 추천했다면 더 올리지 말아라
			return false;
		}
		CommunityBoard board = boardRepo.findById(bno).get();
		board.setRecommendcnt(board.getRecommendcnt()+1);
		boardRepo.save(board);
		//recommendcnt를 1올림
		
		CommunityRecommend recommend = new CommunityRecommend();
		recommend.setId(id);
		recommendRepo.save(recommend);
		return true;
	}
	@Transactional
	@Override
	public boolean downRecommendcnt(String userid, Long bno) {
		// TODO Auto-generated method stub
		RecommendId id = new RecommendId(userid,bno);
		// TODO Auto-generated method stub
		if(recommendRepo.existsById(id)) {
			//이미 반대했다면 더 올리지 말아라
			return false;
		}
		CommunityBoard board = boardRepo.findById(bno).get();
		board.setRecommendcnt(board.getRecommendcnt()-1);
		boardRepo.save(board);
		//recommendcnt를 1내림
		
		CommunityRecommend recommend = new CommunityRecommend();
		recommend.setId(id);
		recommendRepo.save(recommend);
		return true;
	}

}
