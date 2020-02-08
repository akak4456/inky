package org.akak4456.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.domain.CommunityUploadFile;
import org.akak4456.persistence.CommunityBoardRepository;
import org.akak4456.vo.BoardForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class CommunityBoardServiceImpl implements CommunityBoardService{
	@Autowired
	private CommunityBoardRepository repo;
	@Transactional
	@Override
	public boolean save(BoardForm boardForm)  {
		CommunityBoard board = new CommunityBoard();
		board.setUserid(boardForm.getUserid());
		board.setTitle(boardForm.getTitle());
		board.setContent(boardForm.getContent());
		/*
		 * boardForm.getFileForm().forEach(fileForm->{ log.info("fileForm: "+fileForm);
		 * });
		 */
		List<CommunityUploadFile> communityUploadFiles = new ArrayList<>();
		boardForm.getFileForm().forEach(fileForm->{
			CommunityUploadFile uploadFile = new CommunityUploadFile();
			uploadFile.setUploadPath(fileForm.getUploadPath());
			uploadFile.setUploadFileName(fileForm.getUploadFileName());
			communityUploadFiles.add(uploadFile);
		});
		board.setUploads(communityUploadFiles);
		
		board = repo.save(board);
		return true;
	}
	@Override
	public Page<CommunityBoard> getListWithPaging(String type, String keyword,Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(repo.makePredicate(type, keyword),pageable);
	}
	@Override
	public CommunityBoard getOne(Long bno) {
		// TODO Auto-generated method stub
		Optional<CommunityBoard> result = repo.findById(bno);
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}
	@Override
	public boolean update(BoardForm boardForm) {
		// TODO Auto-generated method stub
		CommunityBoard board = new CommunityBoard();
		board.setBno(boardForm.getBno());
		board.setUserid(boardForm.getUserid());
		board.setTitle(boardForm.getTitle());
		board.setContent(boardForm.getContent());
		board.setRegdate(boardForm.getRegdate());
		/*
		 * boardForm.getFileForm().forEach(fileForm->{ log.info("fileForm: "+fileForm);
		 * });
		 */
		List<CommunityUploadFile> communityUploadFiles = new ArrayList<>();
		boardForm.getFileForm().forEach(fileForm->{
			CommunityUploadFile uploadFile = new CommunityUploadFile();
			uploadFile.setUploadPath(fileForm.getUploadPath());
			uploadFile.setUploadFileName(fileForm.getUploadFileName());
			log.info("fileForm..."+fileForm);
			communityUploadFiles.add(uploadFile);
		});
		log.info("communityUploadFiles..."+communityUploadFiles);
		board.setUploads(communityUploadFiles);
		board = repo.save(board);
		return true;
	}
}
