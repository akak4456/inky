package org.akak4456.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.akak4456.constant.Constants;
import org.akak4456.constant.RegexpCheckConstants;
import org.akak4456.service.MemberService;
import org.akak4456.service.MemberService.Emailok;
import org.akak4456.service.MemberService.Idok;
import org.akak4456.vo.EmailCodeCheckVO;
import org.akak4456.vo.FindpwVO;
import org.akak4456.vo.MemberForm;
import org.akak4456.vo.MemberModifyFormVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/member/")
public class MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private JavaMailSender javaMailSender;
	@GetMapping("/join")
	public void join() {

	}
	@GetMapping("/findpw")
	public void findpw() {
		
	}
	@GetMapping("/getmy")
	public void getmy() {
		
	}
	@GetMapping("/modify")
	public void modify() {
		
	}

	@Transactional
	@PostMapping("/join")
	@ResponseBody
	public ResponseEntity<String> joinPost(@Valid @RequestBody MemberForm memberForm, BindingResult bindingResult,HttpSession session) {
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
			return new ResponseEntity<>(Constants.MAKE_MEMBER_FAIL_FOR_ID, HttpStatus.BAD_REQUEST);
		}
		if(!memberService.ExistEmail(memberForm.getUemail()).equals(Emailok.OK)) {
			return new ResponseEntity<>(Constants.MAKE_MEMBER_FAIL_FOR_EMAIL, HttpStatus.BAD_REQUEST);
		}
		String checkedEmail = session.getAttribute("checkedEmail")==null?null:(String)(session.getAttribute("checkedEmail"));
		if(checkedEmail == null) {
			return new ResponseEntity<>(Constants.MAKE_MEMBER_FAIL_FOR_EMAIL, HttpStatus.BAD_REQUEST);
		}
		session.removeAttribute("checkedEmail");
		
		
		memberService.addMember(memberForm);
		return new ResponseEntity<>(Constants.MAKE_MEMBER_SUCCESS, HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/checkid")
	@ResponseBody
	public ResponseEntity<String> checkId(@RequestBody String uid) {
		log.info("CHECK UID..." + uid);
		Idok isok = memberService.ExistId(uid);
		if (isok.equals(Idok.NOTAVAILABLE)) {
			return new ResponseEntity<>(Constants.ID_FORMAT_NOTAVAILABLE, HttpStatus.BAD_REQUEST);
		}
		if (isok.equals(Idok.EXIST))
			return new ResponseEntity<>(Constants.ID_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(Constants.MAKE_MEMBER_SUCCESS_FOR_ID, HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/sendEmail")
	@ResponseBody
	public ResponseEntity<String> sendEmail(@RequestBody String uemail,HttpSession session) {
		/*
		 * 이메일 주소가 잘못되면 어찌할 것인가
		 * 그거에 대해서 생각하기!!!!!
		 */
		log.info("sendEmail..."+uemail);
		if(!memberService.ExistEmail(uemail).equals(Emailok.OK)) {
			return new ResponseEntity<>(Constants.EMAIL_FORMAT_NOTAVAILABLE,HttpStatus.BAD_REQUEST);
		}
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(uemail);

		String code = generateCode();

		msg.setSubject("인증코드는 [" + code + "]입니다.");
		msg.setText("인증코드는 [" + code + "]입니다.\n이 코드를 입력해주세요");

		javaMailSender.send(msg);
		
		
		session.setAttribute("code", code);
		
		log.info("sendEmailDone...");
		return new ResponseEntity<>(Constants.SEND_EMAIL, HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/checkEmail")
	@ResponseBody
	public ResponseEntity<String> checkEmail(@RequestBody EmailCodeCheckVO emailCodeCheckVO,HttpSession session) {
		log.info("checkemail..."+emailCodeCheckVO.getEmail()+" "+emailCodeCheckVO.getCode());
		String storedCode = session.getAttribute("code") == null?null:(String)session.getAttribute("code");
		log.info("storedCode..."+storedCode);
		if (storedCode == null||!storedCode.equals(emailCodeCheckVO.getCode())) {
			return new ResponseEntity<>(Constants.EMAIL_CODE_NOTAVAILABLE, HttpStatus.BAD_REQUEST);
		}
		session.removeAttribute("code");
		session.setAttribute("checkedEmail", emailCodeCheckVO.getEmail());
		return new ResponseEntity<>(Constants.EMAIL_CODE_AVAILABLE, HttpStatus.OK);
	}
	
	@Transactional
	@PostMapping("/findpw")
	@ResponseBody
	public ResponseEntity<String> findpw(@RequestBody FindpwVO findpwVO){
		log.info("findpw..."+findpwVO.getUid() +" "+findpwVO.getUemail());
		if(!findpwVO.getUid().matches(RegexpCheckConstants.ID_REGEXP)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(!findpwVO.getUemail().matches(RegexpCheckConstants.EMAIL_REGEXP)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(!memberService.ExistMemberForIdAndEmail(findpwVO.getUid(), findpwVO.getUemail()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		String newPW = generateNewPW();
		memberService.updatePW(findpwVO.getUid(), newPW);
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(findpwVO.getUemail());

		msg.setSubject("새로운 비밀번호입니다.");
		msg.setText("새로운 비밀번호는 "+newPW+"입니다. 이 비밀번호로 로그인 하신뒤에 회원정보변경을 부탁드립니다.");

		javaMailSender.send(msg);
		
		return new ResponseEntity<>(Constants.SEND_NEW_PW,HttpStatus.OK);
	}
	
	@PreAuthorize("#userid == authentication.principal.member.uid")
	@DeleteMapping("/delete/{userid}")
	@ResponseBody
	public ResponseEntity<String> deleteUser(@PathVariable("userid")String userid){
		memberService.deleteMember(userid);
		return new ResponseEntity<>(Constants.REMOVE_MEMBER_SUCCESS,HttpStatus.OK);
	}
	
	@PreAuthorize("#mem.uid == authentication.principal.member.uid")
	@PostMapping("/modify")
	@ResponseBody
	public ResponseEntity<String> modifyUser(@RequestBody MemberModifyFormVO mem,HttpSession session){
		log.info("member modify...");
		if(!memberService.updateMember(mem))
			return new ResponseEntity<>(Constants.MODIFY_MEMBER_FAIL,HttpStatus.BAD_REQUEST);
		session.invalidate();
		return new ResponseEntity<>(Constants.MODIFY_MEMBER_SUCCESS,HttpStatus.OK);
	}
	
	private String generateNewPW() {
		//개선된 비밀번호 만들기 방식을 만들것
		String newPW = "";
		Random r = new Random();
		while(!(newPW.length()>=10&&newPW.matches(RegexpCheckConstants.PW_REGEXP))) {
			char c = 'a';
			switch(r.nextInt(3)) {
			case 0:
				//문자열을 더해라
				c = (char)(r.nextInt(26) + 'a');
				break;
			case 1:
				//숫자를 더해라
				c = (char)(r.nextInt(10)+'0');
				break;
			case 2:
				//특수문자를 더해라
				int newChar = r.nextInt(3);
				if(newChar == 0) {
					c = '!';
				}else if(newChar == 1) {
					c = '?';
				}else {
					c = '#';
				}
			}
			newPW += c;
			if(newPW.length() >= 40)
				newPW = "";
		}
		return newPW;
	}
	

	private String generateCode() {
		String ret = "";
		for (int i = 0; i < 6; i++) {
			int t = (int) (Math.random() * 10);
			ret += t;
		}
		return ret;
	}
}