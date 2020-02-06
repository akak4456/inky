package org.akak4456.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardForm {
	private String userid;
	private String kind;
	private String title;
	private String content;
	private List<FileForm> fileForm;
}
