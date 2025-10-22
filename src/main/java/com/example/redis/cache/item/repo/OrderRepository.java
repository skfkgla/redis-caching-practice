package com.example.redis.cache.item.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.redis.cache.item.domain.ItemOrder;

public interface OrderRepository extends JpaRepository<ItemOrder, Long> {}
