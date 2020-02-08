package org.akak4456.vo;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardForm {
	private Long bno;
	private String userid;
	private String kind;
	private String title;
	private String content;
	private List<FileForm> fileForm;
	private Timestamp regdate;
}
