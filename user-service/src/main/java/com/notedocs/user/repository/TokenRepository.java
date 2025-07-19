package com.notedocs.user.repository;

import com.notedocs.user.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
}
