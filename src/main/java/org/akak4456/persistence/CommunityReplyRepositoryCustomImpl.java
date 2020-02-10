package org.akak4456.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.akak4456.domain.CommunityReply;
import org.akak4456.domain.QCommunityReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.jpa.JPQLQuery;

import lombok.extern.java.Log;
@Log
public class CommunityReplyRepositoryCustomImpl extends QuerydslRepositorySupport
		implements CommunityReplyRepositoryCustom {
	public CommunityReplyRepositoryCustomImpl() {
		super(CommunityReply.class);
	}
	@Transactional
	@Override
	public Page<CommunityReply> getListWithPaging(Pageable page) {
		// TODO Auto-generated method stub
		QCommunityReply reply = QCommunityReply.communityReply;
		JPQLQuery<CommunityReply> query = from(reply)
				.where(reply.rno.gt(0)).where(reply.parent.isNull());
		query.orderBy(reply.rno.asc());
		query.offset(page.getOffset());
		query.limit(page.getPageSize());
		List<CommunityReply> result = query.fetch();
		/*result.forEach(r->{
			log.info(r.getReply());
			List<CommunityReply> child = r.getChildren();
			child.forEach(t->log.info(t.getReply()));
		});*/
		List<CommunityReply> ret = new ArrayList<>();
		//JPQLQuery<Tuple> tuple = query.select(reply.rno,reply.replier,reply.reply);
		for(CommunityReply repl:result) {
			ret.add(repl);
			for(CommunityReply rerepl: repl.getChildren()) {
				if(ret.size() >= page.getPageSize())
					break;
				ret.add(rerepl);
			}
			if(ret.size() >= page.getPageSize())
				break;
		}
		return new PageImpl<>(ret,page,ret.size());
	}

}
