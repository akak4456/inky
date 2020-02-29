package org.akak4456.service;

import java.util.ArrayList;
import java.util.List;

import org.akak4456.domain.Member;
import org.akak4456.domain.MemberRole;
import org.akak4456.persistence.MemberRepository;
import org.akak4456.vo.MemberForm;
import org.akak4456.vo.MemberModifyFormVO;
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

	@Override
	public boolean ExistMemberForIdAndEmail(String uid, String uemail) {
		// TODO Auto-generated method stub
		if(!memberRepo.findById(uid).isPresent())
			//아이디가 존재하지 않으면
			return false;
		Member mem = memberRepo.findById(uid).get();
		if(!mem.getUemail().equals(uemail))
			//기존 회원정보의 이메일과 같지 않으면
			return false;
		return true;
	}

	@Override
	public boolean updateMember(MemberModifyFormVO member) {
		// TODO Auto-generated method stub
		if(!memberRepo.findById(member.getUid()).isPresent())
			return false;
		Member mem = memberRepo.findById(member.getUid()).get();
		if(member.getUname() != null && member.getUname().length() > 0) {
			mem.setUname(member.getUname());
		}
		if(member.getUpw() != null && member.getUpw().length() > 0) {
			if(!member.getUpw().matches(MemberForm.pwregexp))
				return false;
			String encryptPw = pwEncoder.encode(member.getUpw());
			mem.setUpw(encryptPw);
		}
		mem.getUploads().clear();
		if(member.getUploads() != null && member.getUploads().size() > 0) {
			mem.getUploads().addAll(member.getUploads());
		}
		memberRepo.save(mem);
		
		return true;
	}

	@Override
	public void updatePW(String uid, String upw) {
		// TODO Auto-generated method stub
		if(!memberRepo.findById(uid).isPresent())
			return;
		Member mem = memberRepo.findById(uid).get();
		String encryptPw = pwEncoder.encode(upw);
		mem.setUpw(encryptPw);
		memberRepo.save(mem);
	}

	@Override
	public void deleteMember(String uid) {
		// TODO Auto-generated method stub
		memberRepo.deleteById(uid);
	}
}
