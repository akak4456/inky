package org.akak4456.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="tbl_report")
@EqualsAndHashCode(of="rno")
@ToString(exclude="roles")
public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	
	private Long target_no;
	
	private char target_kind;
	
	private String reason;
	
	private String title;
	
	private String content;
	
	private String reporterId;
}
