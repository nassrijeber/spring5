package com.acme.ex3.repository;

import com.acme.ex3.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByAccountUsername(String username);
}
