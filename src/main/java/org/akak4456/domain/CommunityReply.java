package org.akak4456.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude= {"board","children"})
@Entity
@Table(name = "tbl_community_reply",
indexes = {@Index(unique=false,columnList="board_bno")})
@EqualsAndHashCode(of="rno")
public class CommunityReply extends Reply {
	@ManyToOne
	private CommunityBoard board;
	
	@OneToMany
	@JoinColumn(name = "parent_rno")
    private List<CommunityReply> children;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parent_rno",insertable=false,updatable=false)
	private CommunityReply parent;
}
