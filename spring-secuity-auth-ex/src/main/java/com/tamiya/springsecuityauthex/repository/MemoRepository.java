package com.tamiya.springsecuityauthex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tamiya.springsecuityauthex.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {

}
