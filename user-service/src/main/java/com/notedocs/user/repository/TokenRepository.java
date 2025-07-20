package com.notedocs.user.repository;

import com.notedocs.user.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
SELECT t FROM Token t inner join User u on t.user.id =u.id
where u.id=:userId and (t.expired=false or t.revoked =false )

""")
    List<Token> findAllValidTokensByUserId(int userId);
}
