package org.akak4456.domain;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude="board")
@Entity
@Table(name = "tbl_community_reply",
indexes = {@Index(unique=false,columnList="board_bno")})
@EqualsAndHashCode(of="rno")
public class CommunityReply extends Reply {
	@ManyToOne
	private CommunityBoard board;
}
