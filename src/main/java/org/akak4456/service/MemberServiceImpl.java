package org.akak4456.service;

import java.util.ArrayList;
import java.util.List;

import org.akak4456.domain.Member;
import org.akak4456.domain.MemberRole;
import org.akak4456.persistence.MemberRepository;
import org.akak4456.vo.MemberForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private PasswordEncoder pwEncoder;
	@Autowired
	private MemberRepository memberRepo;

	@Override
	public boolean addMember(MemberForm memberForm) {
		// TODO Auto-generated method stub
		String encryptPw = pwEncoder.encode(memberForm.getUpw());
		List<MemberRole> roles = new ArrayList<>();
		MemberRole role = new MemberRole();
		role.setRoleName("BASIC");
		roles.add(role);
		Member member = new Member();
		member.setUid(memberForm.getUid());
		member.setUpw(encryptPw);
		member.setUname(memberForm.getUname());
		member.setUemail(memberForm.getUemail());
		member.setUploads(memberForm.getUploads());
		member.setRoles(roles);
		memberRepo.save(member);
		return true;
	}

	@Override
	public Idok ExistId(String uid) {
		// TODO Auto-generated method stub
		if (!uid.matches(MemberForm.idregexp)) {
			return Idok.NOTAVAILABLE;
		}
		if (memberRepo.findById(uid).isPresent())
			return Idok.EXIST;
		return Idok.OK;
	}

	@Override
	public Emailok ExistEmail(String uemail) {
		// TODO Auto-generated method stub
		if(!uemail.matches(MemberForm.emailregexp))
			return Emailok.NOTAVAILABLE;
		if(memberRepo.findByUemail(uemail).size() > 0) {
			return Emailok.EXIST;
		}
		return Emailok.OK;
	}
}
