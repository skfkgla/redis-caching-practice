package com.example.redis.item.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

// 나머지 import 문들

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	@Primary
	// CacheManager로 진행해도 정상 동작
	public RedisCacheManager CacheManager(
		RedisConnectionFactory redisConnectionFactory
	) {
		// 설정 구성을 먼저 진행한다.
		// Redis를 이용해서 Spring Cache를 사용할 때
		// Redis 관련 설정을 모아두는 클래스
		RedisCacheConfiguration configuration = RedisCacheConfiguration
			.defaultCacheConfig()
			// null을 캐싱 할것인지
			.disableCachingNullValues()
			// 기본 캐시 유지 시간 (Time To Live)
			.entryTtl(Duration.ofSeconds(10))
			// 캐시를 구분하는 접두사 설정
			.computePrefixWith(CacheKeyPrefix.simple())
			// 캐시에 저장할 값을 어떻게 직렬화 / 역직렬화 할것인지
			.serializeValuesWith(
				SerializationPair.fromSerializer(RedisSerializer.java())
			);

		return RedisCacheManager
			.builder(redisConnectionFactory)
			//기본값을 해당 configuration 설정 해주는 메서드
			.cacheDefaults(configuration)
			.build();
	}
}