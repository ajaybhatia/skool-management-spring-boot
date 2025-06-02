package com.ajaybhatia.cloud_gateway.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajaybhatia.cloud_gateway.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
  User findByUsername(String username);
}
