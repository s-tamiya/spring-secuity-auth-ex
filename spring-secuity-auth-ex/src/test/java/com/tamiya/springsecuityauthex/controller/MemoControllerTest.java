package com.tamiya.springsecuityauthex.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamiya.springsecuityauthex.entity.Memo;
import com.tamiya.springsecuityauthex.service.MemoService;;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MemoController.class, secure = false)
public class MemoControllerTest {

  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper objectMapper;

  // サービスの使用
  @MockBean
  private MemoService memoService;

   final private MediaType contentTypeJson = new MediaType(
       MediaType.APPLICATION_JSON.getType(),
       MediaType.APPLICATION_JSON.getSubtype(),
       Charset.forName("utf8")
       );

   @Test
   public void getMemo() throws Exception{
    Memo expected = new Memo(1L, "test_memo", "test_description", false, LocalDateTime.of(2018, 8, 24, 0, 34, 49));
    String expectedJson = objectMapper.writeValueAsString(expected);
    Mockito.when(memoService.findById(anyLong())).thenReturn(Optional.ofNullable(expected));

    RequestBuilder builder = MockMvcRequestBuilders.get("/memo/{id}", 1L)
        .accept(contentTypeJson);

    mvc.perform(builder)
      .andExpect(status().isOk())
      .andExpect(content().contentType(contentTypeJson))
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.title").value(expected.getTitle()))
      .andExpect(jsonPath("$.description").value(expected.getDescription()))
      .andExpect(jsonPath("$.done").value(expected.getDone()))
      .andDo(print());
  }
}

