package org.akak4456.controller;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.domain.NotifyBoard;
import org.akak4456.domain.QnABoard;
import org.akak4456.domain.TechBoard;
import org.akak4456.service.BoardService;
import org.akak4456.service.NotifyBoardService;
import org.akak4456.vo.PageMaker;
import org.akak4456.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.java.Log;

@Controller
@Log
public class IndexController {
	@Autowired
	@Qualifier("communityBoardServiceImpl")
	private BoardService communityBoardService;
	@Autowired
	@Qualifier("techBoardServiceImpl")
	private BoardService techBoardService;
	@Autowired
	@Qualifier("qnABoardServiceImpl")
	private BoardService qnaBoardService;
	@Autowired
	private NotifyBoardService notifyBoardService;
	@GetMapping("/")
	public String getIndex(Model model) {
		Page<CommunityBoard> community = communityBoardService.getListWithPaging(null, null, new PageVO().makePageable(0, "bno"));
		Page<TechBoard> tech = techBoardService.getListWithPaging(null, null, new PageVO().makePageable(0, "bno"));
		Page<QnABoard> qna = qnaBoardService.getListWithPaging(null, null, new PageVO().makePageable(0, "bno"));
		model.addAttribute("community", new PageMaker<CommunityBoard>(community));
		model.addAttribute("tech", new PageMaker<TechBoard>(tech));
		model.addAttribute("qna", new PageMaker<QnABoard>(qna));
		Page<NotifyBoard> notify = notifyBoardService.getList(new PageVO().makePageable(0, "bno"));
		model.addAttribute("notify", new PageMaker<NotifyBoard>(notify));
		model.addAttribute("pageVO",new PageVO());
		return "/index";
	}
}
