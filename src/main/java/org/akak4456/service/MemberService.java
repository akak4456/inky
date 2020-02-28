package org.akak4456.service;

import org.akak4456.vo.MemberForm;

public interface MemberService {
	enum Idok {EXIST,OK,NOTAVAILABLE};
	
	enum Emailok {EXIST,OK,NOTAVAILABLE};
	
	public boolean addMember(MemberForm member);
	
	public Idok ExistId(String uid);
	
	public Emailok ExistEmail(String uemail);
	
	public boolean ExistMemberForIdAndEmail(String uid,String uemail);
	
	public void updatePW(String uid,String upw);
	
	public void updateMember(MemberForm member);
	
	public void deleteMember(String uid);
}
