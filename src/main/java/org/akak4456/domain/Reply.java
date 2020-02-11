package org.akak4456.domain;

import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	
	private Long parent_rno;
	
	private String path;
	
	private String reply;
	
	private String replier;
	
	@CreationTimestamp
	private Timestamp replydate;
	@UpdateTimestamp
	private Timestamp updatedate;
	
	@PostPersist
	public void onLoad() {
		String str1 = "";
		//백진법으로 path저장
		if(this.parent_rno != null) {
			str1 = ""+this.parent_rno;
			str1 = String.format("%64s", str1).replace(' ', '0');
		}
		String str2 = "";
		if(this.rno != null) {
			str2 = ""+this.rno;
			str2 = String.format("%64s", str2).replace(' ', '0');
		}
		this.path= str1+str2;
	}
}
