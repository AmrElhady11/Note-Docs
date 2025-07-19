package com.notedocs.user.repository;

import com.notedocs.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
