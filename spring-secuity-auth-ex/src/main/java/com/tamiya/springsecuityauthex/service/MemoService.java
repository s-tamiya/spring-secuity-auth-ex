package com.tamiya.springsecuityauthex.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tamiya.springsecuityauthex.entity.Memo;

public interface MemoService {

  Optional<Memo> findById(Long id);
  Page<Memo> findAll(Pageable page);
  void store(Memo memo);
  void removeById(Long id);

}
