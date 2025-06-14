package com.ajaybhatia.skool.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajaybhatia.skool.model.School;

public interface SchoolRepository extends JpaRepository<School, UUID> {
}
