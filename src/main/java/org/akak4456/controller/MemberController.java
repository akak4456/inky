package org.akak4456.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.akak4456.domain.Member;
import org.akak4456.domain.MemberRole;
import org.akak4456.persistence.MemberRepository;
import org.akak4456.vo.EmailCodeCheckVO;
import org.akak4456.vo.MemberForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private static Map<String, String> storeCode = new HashMap<String, String>();

	private static Set<String> emailChecked = new HashSet<String>();

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private PasswordEncoder pwEncoder;

	@Autowired
	private MemberRepository repo;

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
		member.setRoles(roles);
		repo.save(member);
		return new ResponseEntity<>("성공", HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/checkid")
	@ResponseBody
	public ResponseEntity<String> checkId(@RequestBody String uid) {
		log.info("CHECK UID..." + uid);
		if (!uid.matches(MemberForm.idregexp)) {
			return new ResponseEntity<>("이 아이디는 만들 수 없습니다!", HttpStatus.BAD_REQUEST);
		}
		if (repo.findById(uid).isPresent())
			return new ResponseEntity<>("아이디가 이미 존재 합니다!", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>("아이디를 만들 수 있습니다!", HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/sendEmail")
	@ResponseBody
	public ResponseEntity<String> sendEmail(@RequestBody String uemail) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(uemail);

		String code = generateCode();

		msg.setSubject("인증코드는 [" + code + "]입니다.");
		msg.setText("인증코드는 [" + code + "]입니다.\n이 코드를 입력해주세요");

		javaMailSender.send(msg);

		storeCode.put(uemail, code);

		return new ResponseEntity<>("메일에 인증코드를 보냈습니다! 인증코드를 확인해주세요", HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/checkEmail")
	@ResponseBody
	public ResponseEntity<String> checkEmail(@RequestBody EmailCodeCheckVO emailCodeCheckVO) {
		if (storeCode.get(emailCodeCheckVO.getEmail()) == null
				|| !storeCode.get(emailCodeCheckVO.getEmail()).equals(emailCodeCheckVO.getCode())) {
			return new ResponseEntity<>("인증코드가 유효하지 않습니다!", HttpStatus.BAD_REQUEST);
		}
		emailChecked.add(emailCodeCheckVO.getEmail());
		return new ResponseEntity<>("메일에 인증코드를 보냈습니다! 인증코드를 확인해주세요", HttpStatus.OK);
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
