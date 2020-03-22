package org.akak4456.service;

import java.io.IOException;
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
	public void save(T board)  {
		/*
		 * boardForm.getFileForm().forEach(fileForm->{ log.info("fileForm: "+fileForm);
		 * });
		 */
		board = repo.save(board);
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
	public void update(T board) {
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
	}
	@Transactional
	public void deleteBoard(Long bno) throws IOException {
		// TODO Auto-generated method stub
		//파일부터 삭제
		List<UploadFile> uploadFiles = repo.findById(bno).get().getUploads();
		for(UploadFile uploadFile:uploadFiles) {
			if(!fileService.deleteFile("/"+uploadFile.getUploadPath(), uploadFile.getUploadFileName())) {
				throw new IOException("파일이 존재하지 않습니다!");
			}
		}
		repo.deleteById(bno);
	}
}
