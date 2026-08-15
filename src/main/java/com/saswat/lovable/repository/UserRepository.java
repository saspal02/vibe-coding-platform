package com.saswat.lovable.repository;

import com.saswat.lovable.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
