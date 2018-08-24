package com.tamiya.springsecuityauthex.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
// @SpringBootTestを使用する結合テスト
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelloControllerIntegrationTest {

  // コンテキスト
  @Autowired
  private WebApplicationContext context;

  // モックMVCは@beforeでビルドする
  private MockMvc mvc;

  final private MediaType contentTypeText = new MediaType(
      MediaType.TEXT_PLAIN.getType(), MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("utf8")
      );

  @Before
  public void setup() {
    // MockMvcBuildersを使用してMockMvcをビルドする
    mvc = MockMvcBuilders
            .webAppContextSetup(context) // コンテキストの指定
            .apply(springSecurity()) // SpringSecurity有効
            .build(); // ビルド
  }

  @WithMockUser
  @Test
  public void greeting() throws Exception {
    //User user = new User(1L, "test_user", "pass", "aaa.bbb@example.com", true);
    //SimpleLoginUser loginUser = new SimpleLoginUser(user);

    RequestBuilder builder = MockMvcRequestBuilders.get("/hello")
        //.with(user(loginUser))
        // .with(csrf())
        .accept(contentTypeText);// contentTypeの設定

    mvc.perform(builder)
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentTypeText))
        .andExpect(content().string("hello world"))
        .andDo(print());
  }

}
