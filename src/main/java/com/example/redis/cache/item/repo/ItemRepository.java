package com.example.redis.cache.item.repo;

import com.example.redis.cache.item.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAllByNameContains(String name, Pageable pageable);
}
