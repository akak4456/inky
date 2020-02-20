package org.akak4456.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.domain.CommunityUploadFile;
import org.akak4456.persistence.CommunityBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class CommunityBoardServiceImpl implements BoardService<CommunityBoard>{
	@Autowired
	private CommunityBoardRepository repo;
	@Autowired
	private FileService fileService;
	@Transactional
	@Override
	public boolean save(CommunityBoard board)  {
		/*
		 * boardForm.getFileForm().forEach(fileForm->{ log.info("fileForm: "+fileForm);
		 * });
		 */
		board = repo.save(board);
		return true;
	}
	@Transactional
	@Override
	public Page<CommunityBoard> getListWithPaging(String type, String keyword,Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(repo.makePredicate(type, keyword),pageable);
	}
	@Transactional
	@Override
	public CommunityBoard getOne(Long bno) {
		// TODO Auto-generated method stub
		Optional<CommunityBoard> result = repo.findById(bno);
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}
	@Transactional
	@Override
	public boolean update(CommunityBoard board) {
		// TODO Auto-generated method stub
		//log.info("uploads..."+(board.getUploads()==null));
		CommunityBoard newBoard = repo.findById(board.getBno()).get();
		newBoard.setContent(board.getContent());
		newBoard.setTitle(board.getTitle());
		newBoard.getUploads().clear();
		if(board.getUploads() != null&&board.getUploads().size() > 0) {
			newBoard.getUploads().addAll(board.getUploads());
		}
		newBoard = repo.save(newBoard);
		return true;
	}
	@Transactional
	@Override
	public boolean deleteBoard(Long bno) {
		// TODO Auto-generated method stub
		//파일부터 삭제
		List<CommunityUploadFile> uploadFiles = repo.findById(bno).get().getUploads();
		for(CommunityUploadFile uploadFile:uploadFiles) {
			if(!fileService.deleteFile("/"+uploadFile.getUploadPath(), uploadFile.getUploadFileName())) {
				return false;
			}
		}
		repo.deleteById(bno);
		return true;
	}
}
