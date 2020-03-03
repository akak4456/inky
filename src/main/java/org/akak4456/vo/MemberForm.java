package org.akak4456.vo;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.akak4456.constant.RegexpCheckConstants;
import org.akak4456.domain.MemberProfile;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MemberForm {
	@NotNull(message=RegexpCheckConstants.ID_NOTNULL_FAIL)
	@Pattern(regexp=RegexpCheckConstants.ID_REGEXP,
	message=RegexpCheckConstants.ID_REGEXP_FAIL)
	private String uid;
	
	@NotNull(message=RegexpCheckConstants.PW_NOTNULL_FAIL)
	@Pattern(regexp=RegexpCheckConstants.PW_REGEXP,
			message=RegexpCheckConstants.PW_REGEXP_FAIL)
	private String upw;
	
	@NotNull(message=RegexpCheckConstants.EMAIL_NOTNULL_FAIL)
	@NotEmpty(message=RegexpCheckConstants.EMAIL_NOTNULL_FAIL)
	@Pattern(regexp=RegexpCheckConstants.EMAIL_REGEXP,message=RegexpCheckConstants.EMAIL_REGEXP_FAIL)
	private String uemail;
	
	@NotNull(message=RegexpCheckConstants.NAME_NOTNULL_FAIL)
	@NotEmpty(message=RegexpCheckConstants.NAME_NOTNULL_FAIL)
	private String uname;
	
	private List<MemberProfile> uploads;
}
