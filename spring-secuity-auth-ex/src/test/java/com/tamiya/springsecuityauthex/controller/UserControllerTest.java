package com.tamiya.springsecuityauthex.controller;

import java.nio.charset.Charset;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AcceptAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.tamiya.springsecuityauthex.entity.User;
import com.tamiya.springsecuityauthex.security.SecurityConfig;
import com.tamiya.springsecuityauthex.security.SimpleLoginUser;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class) // テストするクラス
@Import(value = {SecurityConfig.class}) // SecurityConfigのインポート 
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean(name = "simpleUserDetailsService")
	private UserDetailsService userDetailsService;
	
	// ContentType text plain
	final private MediaType contentTypeText = new MediaType(
			MediaType.TEXT_PLAIN.getType(),
			MediaType.TEXT_PLAIN.getSubtype(),
			Charset.forName("utf8")
			);
	
	// ContentType JSON
	final private MediaType contentTypeJson = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8")
			);
	
	public void greeting() throws Exception {
		User user = new User(1L, "test_user", "pass", "aaa.aaa@example.com", true);
		SimpleLoginUser loginUser = new SimpleLoginUser(user);
		RequestBuilder builder = MockMvcRequestBuilders
				.get("/user")
				.with(user(loginUser))
				.accept(MediaType.TEXT_PLAIN);

		mvc.perform(builder)
			.andExpect(status().isOk())
			.andExpect(authenticated().withUsername("test_user").withRoles("USER", "ADMIN"))
			.andExpect(content().contentType(contentTypeText))
			.andExpect(content().string("hello test_user"))
			.andExpect(cookie().exists("XSRF-TOKEN"))
			.andExpect(forwardedUrl(null))
			.andExpect(redirectedUrl(null))
			.andReturn();
	}
	
	@WithMockUser(roles = "USER")
	@Test
	public void getEcho() throws Exception{
		RequestBuilder builder = MockMvcRequestBuilders.get("/user/echo/{message}", "abc")
				.accept(contentTypeText);
		
		mvc.perform(builder)
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentTypeText))
			.andExpect(content().string("ABC"))
			.andDo(print())
			.andReturn();
	}
}

