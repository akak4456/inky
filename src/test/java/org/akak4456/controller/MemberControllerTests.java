package org.akak4456.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTests {
	
	@Autowired
	private MockMvc mvc;
	@Test
	public void checkid() throws Exception{
		String uid = "asdf123";
		mvc.perform(post("/member/checkid").
				contentType(MediaType.TEXT_PLAIN).
				content(uid))
		.andExpect(status().isOk());
		uid = "aaaa";//글자수가 5자 미만이면 안된다
		mvc.perform(post("/member/checkid").
				contentType(MediaType.TEXT_PLAIN).
				content(uid))
		.andExpect(status().isBadRequest());
	}
}
