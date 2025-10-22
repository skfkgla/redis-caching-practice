package com.example.redis.cart.dto;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Set<CartItemDto> items;
    private Date expireAt;

    public static CartDto fromHashPairs(
            Map<String, Integer> entries,
            Date expireAt
    ) {
        return CartDto.builder()
                .items(entries.entrySet().stream()
                        .map(entry -> CartItemDto.builder()
                                .item(entry.getKey())
                                .count(entry.getValue())
                                .build())
                        .collect(Collectors.toUnmodifiableSet()))
                .expireAt(expireAt)
                .build();
    }
}
