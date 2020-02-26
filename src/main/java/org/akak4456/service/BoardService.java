package org.akak4456.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.akak4456.domain.Board;
import org.akak4456.domain.UploadFile;
import org.akak4456.persistence.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class BoardService<T extends Board> {
	@Autowired
	private BoardRepository<T> repo;
	@Autowired
	private FileService fileService;
	@Transactional
	public boolean save(T board)  {
		/*
		 * boardForm.getFileForm().forEach(fileForm->{ log.info("fileForm: "+fileForm);
		 * });
		 */
		board = repo.save(board);
		return true;
	}
	@Transactional
	public Page<T> getListWithPaging(String type, String keyword,Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(repo.makePredicate(type, keyword),pageable);
	}
	@Transactional
	public T getOne(Long bno) {
		// TODO Auto-generated method stub
		Optional<T> result = repo.findById(bno);
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}
	@Transactional
	public boolean update(T board) {
		// TODO Auto-generated method stub
		//log.info("uploads..."+(board.getUploads()==null));
		T newBoard = repo.findById(board.getBno()).get();
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
	public boolean deleteBoard(Long bno) {
		// TODO Auto-generated method stub
		//파일부터 삭제
		List<UploadFile> uploadFiles = repo.findById(bno).get().getUploads();
		for(UploadFile uploadFile:uploadFiles) {
			if(!fileService.deleteFile("/"+uploadFile.getUploadPath(), uploadFile.getUploadFileName())) {
				return false;
			}
		}
		repo.deleteById(bno);
		return true;
	}
}
