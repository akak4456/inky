package org.akak4456.service;

import org.akak4456.domain.Member;
import org.akak4456.domain.MemberRole;
import org.akak4456.persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private MemberRepository memberRepo;
	@Override
	public boolean upgradeToAdminById(String id) {
		// TODO Auto-generated method stub
		if(!memberRepo.existsById(id))
			return false;
		Member member = memberRepo.findById(id).get();
		MemberRole addRole = new MemberRole();
		addRole.setRoleName("ADMIN");
		member.getRoles().add(addRole);
		memberRepo.save(member);
		return true;
	}
	@Override
	public boolean removeFromAdminById(String id) {
		// TODO Auto-generated method stub
		if(!memberRepo.existsById(id))
			return false;
		Member member = memberRepo.findById(id).get();
		member.getRoles().removeIf(r->r.getRoleName().equals("ADMIN"));
		memberRepo.save(member);
		return true;
	}

}
