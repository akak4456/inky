package org.akak4456.domain;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class UploadFile {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long fno;
	
	private String uploadPath;
	
	private String uploadFileName;
	
	@CreationTimestamp
	private LocalDateTime
	regdate;
	
	
}
