package org.akak4456.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MemberForm {
	public static final String idregexp = "^[A-Za-z0-9+]{8,50}$"; 
	public static final String pwregexp = "^(?=.*[0-9]{1,50})(?=.*[~`!@#$%\\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$";
	@NotNull(message="아이디는 반드시 있어야 합니다")
	@Pattern(regexp=idregexp,
	message="아이디는 영어대소문자 숫자만 가능하며, 총 8글자 이상 50글자 이하 써야합니다")
	private String uid;
	
	@NotNull(message="비밀번호는 반드시 있어야 합니다")
	@Pattern(regexp=pwregexp,
			message="비밀번호는 영어대소문자, 숫자, 특수문자를 각각 1자 이상씩 써야하며, 총 8글자 이상 50글자 이하 써야합니다")
	private String upw;
	
	@NotNull(message="이메일은 반드시 있어야 합니다")
	@NotEmpty(message="이메일은 반드시 있어야 합니다")
	@Email(message="이메일 형식을 맞춰주세요")
	private String uemail;
	
	@NotNull(message="사용자이름은 반드시 있어야 합니다")
	@NotEmpty(message="사용자이름은 반드시 있어야 합니다")
	private String uname;
}
