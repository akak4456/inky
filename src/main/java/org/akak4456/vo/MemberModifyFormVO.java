package org.akak4456.vo;

import java.util.List;

import org.akak4456.domain.MemberProfile;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MemberModifyFormVO {
	private String uid;
	
	//업데이트 과정에서 확인 필수!!!
	private String upw;
	
	//업데이트 과정에서 확인 필수!!!
	private String uname;
	
	private List<MemberProfile> uploads;
}
