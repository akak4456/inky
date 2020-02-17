package org.akak4456.controller;

import java.util.ArrayList;
import java.util.List;

import org.akak4456.domain.Member;
import org.akak4456.domain.MemberRole;
import org.akak4456.persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/member/")
public class MemberController {
	@Autowired
	private PasswordEncoder pwEncoder;
	
	@Autowired
	private MemberRepository repo;
	
	@GetMapping("/join")
	public void join() {
		
	}
	
	@PostMapping("/join")
	public String joinPost(@ModelAttribute("member")Member member) {
		log.info("MEMBER: "+member);
		String encryptPw = pwEncoder.encode(member.getUpw());
		List<MemberRole> roles = new ArrayList<>();
		MemberRole role = new MemberRole();
		role.setRoleName("BASIC");
		roles.add(role);
		member.setUpw(encryptPw);
		member.setRoles(roles);
		repo.save(member);
		return "/member/joinResult";
	}
}
