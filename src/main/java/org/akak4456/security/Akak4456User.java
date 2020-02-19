package org.akak4456.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.akak4456.domain.Member;
import org.akak4456.domain.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Akak4456User extends User {
	private static final String ROLE_PREFIX = "ROLE_";
	
	private Member member;
	
	public Akak4456User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO Auto-generated constructor stub
	}
	
	
	public Akak4456User(Member member) {
		super(member.getUid(),member.getUpw(),makeGrantedAuthority(member.getRoles()));
		this.member = member;
	}
	
	private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles){
		List<GrantedAuthority> list = new ArrayList<>();
		roles.forEach(role->list.add(new SimpleGrantedAuthority(ROLE_PREFIX+role.getRoleName())));
		
		return list;
	}
		
}
