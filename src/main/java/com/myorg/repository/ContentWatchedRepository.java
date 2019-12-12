package com.myorg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myorg.domain.ContentWatched;

public interface ContentWatchedRepository extends JpaRepository<ContentWatched, String> {
}
