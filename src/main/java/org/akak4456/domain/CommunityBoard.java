package org.akak4456.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString(exclude="replies")
@Entity
@Table(name="tbl_community_board")
@EqualsAndHashCode(of="bno")
public class CommunityBoard extends Board {
	@OneToMany(mappedBy="board", cascade=CascadeType.ALL)
	private List<CommunityReply> replies;
}
