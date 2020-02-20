package org.akak4456.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
@Embeddable
public class RecommendId implements Serializable {
	private String userid;
	private Long bno;
	public RecommendId() {
		
	}
	public RecommendId(String userid,Long bno) {
		this.userid = userid;
		this.bno = bno;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Long getBno() {
		return bno;
	}
	public void setBno(Long bno) {
		this.bno = bno;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof RecommendId))return false;
		RecommendId that = (RecommendId) o;
		return Objects.equals(getUserid(),that.getUserid())&&
				Objects.equals(getBno(), that.getBno());
	}
	@Override
	public int hashCode() {
		return Objects.hash(getUserid(),getBno());
	}
}
