package org.akak4456;

import java.util.ArrayList;
import java.util.List;

import org.akak4456.domain.Member;
import org.akak4456.domain.MemberRole;
import org.akak4456.persistence.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class MemberTests {
	@Autowired
	private MemberRepository repo;
	@Autowired
	private PasswordEncoder pwEncoder;
	@Test
	public void testInsert() {
		for(int i=0;i<=100;i++) {
			Member member = new Member();
			member.setUid("user"+i);
			member.setUpw(pwEncoder.encode("pw"+i));
			member.setUname("사용자"+i);
			
			MemberRole role = new MemberRole();
			List<MemberRole> roles = new ArrayList<>();
			if(i <= 90) {
				role.setRoleName("BASIC");
			}else {
				role.setRoleName("ADMIN");
			}
			roles.add(role);
			member.setRoles(roles);
			repo.save(member);
		}
	}
}
