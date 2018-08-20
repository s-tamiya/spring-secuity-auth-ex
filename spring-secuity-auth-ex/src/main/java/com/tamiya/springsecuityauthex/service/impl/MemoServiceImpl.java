package com.tamiya.springsecuityauthex.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tamiya.springsecuityauthex.entity.Memo;
import com.tamiya.springsecuityauthex.repository.MemoRepository;
import com.tamiya.springsecuityauthex.service.MemoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemoServiceImpl implements MemoService {

  private final MemoRepository repository;

  @Autowired
  public MemoServiceImpl(MemoRepository memoRepository) {
    this.repository = memoRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Memo> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Memo> findAll(Pageable page) {
    return repository.findAll(page);
  }

  @Override
  @Transactional(timeout = 10)
  public void store(Memo memo) {
    repository.save(memo);
  }

  @Override
  @Transactional(timeout = 10)
  public void removeById(Long id) {
    repository.deleteById(id);
  }

}
