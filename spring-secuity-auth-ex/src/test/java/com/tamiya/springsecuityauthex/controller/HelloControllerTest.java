package com.tamiya.springsecuityauthex.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
//@WebMvcTestで単体テストを実行するコントローラを指定、セキュリティ=false
@WebMvcTest(value = HelloController.class, secure = false)
public class HelloControllerTest {

  // モックMVCは@Autowiredでビルドする
  @Autowired
  private MockMvc mvc;

  final private MediaType contentTypeText = new MediaType(
      MediaType.TEXT_PLAIN.getType(), MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("utf8")
      );

  @Test
  public void greeting() throws Exception {
    RequestBuilder builder = MockMvcRequestBuilders.get("/hello").accept(MediaType.TEXT_PLAIN);

    MvcResult result = mvc.perform(builder)
          .andExpect(status().isOk())
          .andExpect(content().contentType(contentTypeText))
          .andExpect(content().string("hello world"))
          .andDo(print())
          .andReturn();

      assertThat(result.getResponse().getContentAsString()).isEqualTo("hello world");
  }

  @Test
  public void greetingWithName() throws Exception {
      RequestBuilder builder = MockMvcRequestBuilders.get("/hello/{message}", "WORLD")
              .accept(MediaType.TEXT_PLAIN);

      MvcResult result = mvc.perform(builder)
              .andExpect(status().isOk())
              .andExpect(content().contentType(contentTypeText))
              .andExpect(content().string("hello WORLD"))
              .andDo(print())
              .andReturn();

      assertThat(result.getResponse().getContentAsString()).isEqualTo("hello WORLD");
  }

  @Test
  public void postGreeting() throws Exception {
      RequestBuilder builder = MockMvcRequestBuilders.post("/hello")
              .param("message", "WORLD")
              .accept(MediaType.TEXT_PLAIN);

      MvcResult result = mvc.perform(builder)
              .andExpect(status().isOk())
              .andExpect(content().contentType(contentTypeText))
              .andExpect(content().string("hello WORLD"))
              .andDo(print())
              .andReturn();

      assertThat(result.getResponse().getContentAsString()).isEqualTo("hello WORLD");
  }
}
