package org.akak4456.security;

import org.akak4456.persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
@Service
@Log
public class Akak4456UsersService implements UserDetailsService {
	@Autowired
	MemberRepository repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return
				repo.findById(username)
				.filter(m->m!=null)
				.map(m->new Akak4456User(m)).get();
	}

}
