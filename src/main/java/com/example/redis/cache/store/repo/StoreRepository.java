package com.example.redis.cache.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.redis.cache.store.domain.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {}
