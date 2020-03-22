package org.akak4456.service;

import java.util.ArrayList;
import java.util.List;

import org.akak4456.constant.RegexpCheckConstants;
import org.akak4456.domain.Member;
import org.akak4456.domain.MemberRole;
import org.akak4456.error.EmailExist;
import org.akak4456.error.EmailNotAvailable;
import org.akak4456.error.IdAndEmailNotMatch;
import org.akak4456.error.IdExist;
import org.akak4456.error.IdNotAvailable;
import org.akak4456.error.IdNotExist;
import org.akak4456.error.PasswordNotAvailable;
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
	public void addMember(MemberForm memberForm) {
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
	}

	@Override
	public void ExistId(String uid) throws IdExist, IdNotAvailable {
		// TODO Auto-generated method stub
		if (!uid.matches(RegexpCheckConstants.ID_REGEXP)) {
			throw new IdNotAvailable();
		}
		if (memberRepo.findById(uid).isPresent())
			throw new IdExist();
	}

	@Override
	public void ExistEmail(String uemail) throws EmailExist, EmailNotAvailable {
		// TODO Auto-generated method stub
		if(!uemail.matches(RegexpCheckConstants.EMAIL_REGEXP))
			throw new EmailNotAvailable();
		if(memberRepo.findByUemail(uemail).size() > 0) {
			throw new EmailExist();
		}
	}

	@Override
	public void ExistMemberForIdAndEmail(String uid, String uemail) throws IdNotExist,IdAndEmailNotMatch {
		// TODO Auto-generated method stub
		if(!memberRepo.findById(uid).isPresent())
			//아이디가 존재하지 않으면
			throw new IdNotExist();
		Member mem = memberRepo.findById(uid).get();
		if(!mem.getUemail().equals(uemail))
			//기존 회원정보의 이메일과 같지 않으면
			throw new IdAndEmailNotMatch();
	}

	@Override
	public void updateMember(MemberModifyFormVO member) throws IdNotExist,PasswordNotAvailable {
		// TODO Auto-generated method stub
		if(!memberRepo.findById(member.getUid()).isPresent())
			throw new IdNotExist();
		Member mem = memberRepo.findById(member.getUid()).get();
		if(member.getUname() != null && member.getUname().length() > 0) {
			mem.setUname(member.getUname());
		}
		if(member.getUpw() != null && member.getUpw().length() > 0) {
			if(!member.getUpw().matches(RegexpCheckConstants.PW_REGEXP))
				throw new PasswordNotAvailable();
			String encryptPw = pwEncoder.encode(member.getUpw());
			mem.setUpw(encryptPw);
		}
		mem.getUploads().clear();
		if(member.getUploads() != null && member.getUploads().size() > 0) {
			mem.getUploads().addAll(member.getUploads());
		}
		memberRepo.save(mem);
	}

	@Override
	public void updatePW(String uid, String upw) throws IdNotExist {
		// TODO Auto-generated method stub
		if(!memberRepo.findById(uid).isPresent())
			throw new IdNotExist();
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
