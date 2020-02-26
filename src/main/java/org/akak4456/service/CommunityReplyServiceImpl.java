package org.akak4456.service;

import javax.transaction.Transactional;

import org.akak4456.domain.CommunityBoard;
import org.akak4456.domain.CommunityReply;
import org.akak4456.persistence.CommunityBoardRepository;
import org.akak4456.persistence.CommunityReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
@Service
@Log
public class CommunityReplyServiceImpl extends ReplyService<CommunityBoard,CommunityReply> {

}
