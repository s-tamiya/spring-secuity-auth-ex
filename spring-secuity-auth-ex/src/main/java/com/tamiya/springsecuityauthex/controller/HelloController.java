package com.tamiya.springsecuityauthex.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "hello")
public class HelloController {

  @GetMapping
  public String greeting() {
      return "hello world";
  }

  @GetMapping(path = "{message}")
  public String greeting(@PathVariable(name = "message") String message) {
      return "hello " + message;
  }

  @PostMapping
  public String postGreeting(@RequestParam(name = "message") String message) {
      return "hello " + message;
  }

}
