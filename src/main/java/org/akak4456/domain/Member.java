package org.akak4456.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="tbl_members")
@EqualsAndHashCode(of="uid")
@ToString(exclude="roles")
public class Member {
	@Id
	private String uid;
	
	private String upw;
	
	private String uemail;
	
	private String uname;
	
	@CreationTimestamp
	private LocalDateTime regdate;
	@UpdateTimestamp
	private LocalDateTime updatedate;
	
	@OneToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name="mem")
	private List<MemberRole> roles;
	
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name="uid")
	private List<MemberProfile> uploads;
}
