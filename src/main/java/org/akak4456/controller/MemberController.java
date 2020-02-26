package org.akak4456.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.akak4456.service.MemberService;
import org.akak4456.service.MemberService.Emailok;
import org.akak4456.service.MemberService.Idok;
import org.akak4456.vo.EmailCodeCheckVO;
import org.akak4456.vo.MemberForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/member/")
public class MemberController {
	private final int MAX_SIZE = 1000;
	
	private static Map<String, CodeInfo> storeCode;

	private static Map<String,Date> emailChecked; 

	static {
		storeCode = new HashMap<String, CodeInfo>();
		emailChecked = new HashMap<String,Date>();
	}
	@Autowired
	private MemberService memberService;
	@Autowired
	private JavaMailSender javaMailSender;
	@GetMapping("/join")
	public void join() {

	}

	@Transactional
	@PostMapping("/join")
	@ResponseBody
	public ResponseEntity<String> joinPost(@Valid @RequestBody MemberForm memberForm, BindingResult bindingResult) {
		log.info("MEMBER: " + memberForm);
		if (bindingResult.hasErrors()) {
			// join form 이 맞지 않을 때
			log.info("JOIN FORM MATCH FAILED...");
			for (Object object : bindingResult.getAllErrors()) {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;
					return new ResponseEntity<>(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
				}
			}
		}
		if(!memberService.ExistId(memberForm.getUid()).equals(Idok.OK)) {
			return new ResponseEntity<>("이 아이디로 만들 수 없습니다! 중복확인을 해주세요", HttpStatus.BAD_REQUEST);
		}
		if(!memberService.ExistEmail(memberForm.getUemail()).equals(Emailok.OK)) {
			return new ResponseEntity<>("이 이메일로 만들 수 없습니다!", HttpStatus.BAD_REQUEST);
		}
		if(!emailChecked.containsKey(memberForm.getUemail())) {
			return new ResponseEntity<>("이 이메일로 만들 수 없습니다! 이메일 인증을 해주세요", HttpStatus.BAD_REQUEST);
		}
		//현재 시간과 인증한 시간 차 구하기
		Date reqDate = emailChecked.get(memberForm.getUemail());
		//분단위
		if(diffMinute(reqDate) >= 10) {
			emailChecked.remove(memberForm.getUemail());
			return new ResponseEntity<>("인증코드의 유효기간이 지났습니다. 다시 인증해주세요.", HttpStatus.BAD_REQUEST);
		}
		emailChecked.remove(memberForm.getUemail());
		memberService.addMember(memberForm);
		return new ResponseEntity<>("회원가입에 성공했습니다!", HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/checkid")
	@ResponseBody
	public ResponseEntity<String> checkId(@RequestBody String uid) {
		log.info("CHECK UID..." + uid);
		Idok isok = memberService.ExistId(uid);
		if (isok.equals(Idok.NOTAVAILABLE)) {
			return new ResponseEntity<>("이 아이디는 만들 수 없습니다!", HttpStatus.BAD_REQUEST);
		}
		if (isok.equals(Idok.EXIST))
			return new ResponseEntity<>("아이디가 이미 존재 합니다!", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>("아이디를 만들 수 있습니다!", HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/sendEmail")
	@ResponseBody
	public ResponseEntity<String> sendEmail(@RequestBody String uemail) {
		log.info("sendEmail..."+uemail);
		if(!memberService.ExistEmail(uemail).equals(Emailok.OK)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(uemail);

		String code = generateCode();

		msg.setSubject("인증코드는 [" + code + "]입니다.");
		msg.setText("인증코드는 [" + code + "]입니다.\n이 코드를 입력해주세요");

		javaMailSender.send(msg);
		
		storeCodeAdded(uemail,code);
		
		log.info("sendEmailDone...");
		return new ResponseEntity<>("이메일을 보냈습니다. 인증코드를 확인해주세요!", HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/checkEmail")
	@ResponseBody
	public ResponseEntity<String> checkEmail(@RequestBody EmailCodeCheckVO emailCodeCheckVO) {
		log.info("checkemail..."+emailCodeCheckVO.getEmail()+" "+emailCodeCheckVO.getCode());
		if (storeCode.get(emailCodeCheckVO.getEmail()) == null
				|| !storeCode.get(emailCodeCheckVO.getEmail()).getCode().equals(emailCodeCheckVO.getCode())) {
			return new ResponseEntity<>("인증코드가 유효하지 않습니다!", HttpStatus.BAD_REQUEST);
		}
		storeCode.remove(emailCodeCheckVO.getEmail());
		emailCheckedAdded(emailCodeCheckVO.getEmail());
		return new ResponseEntity<>("인증코드가 유효합니다!", HttpStatus.OK);
	}

	private String generateCode() {
		String ret = "";
		for (int i = 0; i < 6; i++) {
			int t = (int) (Math.random() * 10);
			ret += t;
		}
		return ret;
	}
	
	private long diffMinute(Date reqDate) {
		long reqDateTime = reqDate.getTime();
		Date curDate = new Date();
		long curDateTime = curDate.getTime();
		long minute = (curDateTime - reqDateTime) / 60000;
		return minute;
	}
	
	private void storeCodeAdded(String uemail,String code) {
		if(storeCode.size() >= MAX_SIZE) {
			//필요없는 storeCode삭제
			for(Map.Entry<String, CodeInfo> entry:storeCode.entrySet()) {
				Date reqDate = entry.getValue().getRegdate();
				if(diffMinute(reqDate) >= 10) {
					storeCode.remove(entry.getKey());
				}
			}
		}
		CodeInfo codeInfo = new CodeInfo(code,new Date());
		storeCode.put(uemail, codeInfo);
	}
	
	private void emailCheckedAdded(String uemail) {
		if(emailChecked.size() >= MAX_SIZE) {
			for(Map.Entry<String, Date> entry:emailChecked.entrySet()) {
				Date reqDate = entry.getValue();
				if(diffMinute(reqDate) >= 10) {
					storeCode.remove(entry.getKey());
				}
			}
		}
		emailChecked.put(uemail,new Date());
	}
}
class CodeInfo{
	private String code;
	private Date regdate;
	
	CodeInfo(String code,Date regdate){
		this.code = code;
		this.regdate = regdate;
	}
	
	public String getCode() {
		return code;
	}
	public Date getRegdate() {
		return regdate;
	}
}
