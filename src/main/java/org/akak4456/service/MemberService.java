package org.akak4456.service;

import org.akak4456.error.EmailExist;
import org.akak4456.error.EmailNotAvailable;
import org.akak4456.error.IdAndEmailNotMatch;
import org.akak4456.error.IdExist;
import org.akak4456.error.IdNotAvailable;
import org.akak4456.error.IdNotExist;
import org.akak4456.error.PasswordNotAvailable;
import org.akak4456.vo.MemberForm;
import org.akak4456.vo.MemberModifyFormVO;

public interface MemberService {
	
	public void addMember(MemberForm member);
	
	public void ExistId(String uid) throws IdExist, IdNotAvailable;
	
	public void ExistEmail(String uemail) throws EmailExist, EmailNotAvailable;
	
	public void ExistMemberForIdAndEmail(String uid,String uemail) throws IdNotExist,IdAndEmailNotMatch;
	
	public void updatePW(String uid,String upw) throws IdNotExist;
	
	public void updateMember(MemberModifyFormVO member) throws IdNotExist,PasswordNotAvailable;
	
	public void deleteMember(String uid);
}
