package com.tikam.simple_admin_v2.repository;

import com.tikam.simple_admin_v2.entity.User;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAddress(String emailAddress);

    List<User> findByUserEpIdIn(List<String> epIds);
}
