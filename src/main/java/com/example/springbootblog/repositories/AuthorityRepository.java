package com.example.springbootblog.repositories;


import com.example.springbootblog.models.Account;
import com.example.springbootblog.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
