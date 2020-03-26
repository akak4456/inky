package org.akak4456.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.akak4456.constant.RegexpCheckConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Board <R extends Reply, U extends UploadFile> {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long bno;
	
	@NotNull(message=RegexpCheckConstants.BOARD_TITLE_NOTNULL_FAIL)
	@NotEmpty(message=RegexpCheckConstants.BOARD_TITLE_NOTNULL_FAIL)
	private String title;
	
	private String userid;
	@Column(columnDefinition = "TEXT")
	@NotNull(message=RegexpCheckConstants.BOARD_CONTENT_NOTNULL_FAIL)
	@NotEmpty(message=RegexpCheckConstants.BOARD_CONTENT_NOTNULL_FAIL)
	@Size(min = 1, max = 100, message=RegexpCheckConstants.BOARD_TITLE_MAXLEN_FAIL)
	private String content;
	private Integer recommendcnt = 0;
	private Integer replycnt = 0;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	private LocalDateTime regdate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@UpdateTimestamp
	private LocalDateTime updatedate;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="bno")
	private List<U> uploads;
	
	@OneToMany(mappedBy="board", cascade=CascadeType.ALL)
	private List<R> replies;
}
