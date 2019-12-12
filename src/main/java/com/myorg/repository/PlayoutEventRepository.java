package com.myorg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myorg.domain.PlayoutEvent;

@Repository
public interface PlayoutEventRepository extends JpaRepository<PlayoutEvent, String> {
}
