package org.akak4456.service;

import javax.transaction.Transactional;

import org.akak4456.domain.Board;
import org.akak4456.domain.CommunityBoard;
import org.akak4456.domain.CommunityRecommend;
import org.akak4456.domain.Recommend;
import org.akak4456.domain.RecommendId;
import org.akak4456.persistence.BoardRepository;
import org.akak4456.persistence.RecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class RecommendService <B extends Board, R extends Recommend> {
	@Autowired
	private BoardRepository<B> boardRepo;
	@Autowired
	private RecommendRepository<R> recommendRepo;
	@Transactional
	public boolean upRecommendcnt(String userid, Long bno) {
		RecommendId id = new RecommendId(userid,bno);
		// TODO Auto-generated method stub
		if(recommendRepo.existsById(id)) {
			//이미 추천했다면 더 올리지 말아라
			return false;
		}
		B board = boardRepo.findById(bno).get();
		board.setRecommendcnt(board.getRecommendcnt()+1);
		boardRepo.save(board);
		//recommendcnt를 1올림
		
		R recommend = (R)getRecommend(id);
		recommendRepo.save(recommend);
		return true;
	}
	@Transactional
	public boolean downRecommendcnt(String userid, Long bno) {
		// TODO Auto-generated method stub
		RecommendId id = new RecommendId(userid,bno);
		// TODO Auto-generated method stub
		if(recommendRepo.existsById(id)) {
			//이미 반대했다면 더 올리지 말아라
			return false;
		}
		B board = boardRepo.findById(bno).get();
		board.setRecommendcnt(board.getRecommendcnt()-1);
		boardRepo.save(board);
		//recommendcnt를 1내림
		R recommend = (R)getRecommend(id);
		recommendRepo.save(recommend);

		return true;
	}
	
	public abstract Recommend getRecommend(RecommendId id);
}
