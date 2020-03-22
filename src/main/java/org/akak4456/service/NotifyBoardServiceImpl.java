package org.akak4456.service;

import java.util.Optional;

import org.akak4456.domain.NotifyBoard;
import org.akak4456.persistence.NotifyBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class NotifyBoardServiceImpl implements NotifyBoardService {
	@Autowired
	private NotifyBoardRepository repo;
	@Override
	public void save(NotifyBoard board) {
		// TODO Auto-generated method stub
		repo.save(board);
	}

	@Override
	public Page<NotifyBoard> getList(Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(repo.makePredicate(),pageable);
	}

	@Override
	public NotifyBoard getOne(Long bno) {
		// TODO Auto-generated method stub
		Optional<NotifyBoard> result = repo.findById(bno);
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}

}
