package com.example.redis.cache.item.service;

import com.example.redis.cache.item.domain.Item;
import com.example.redis.cache.item.domain.ItemDto;
import com.example.redis.cache.item.repo.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

	@CachePut(cacheNames = "itemCache", key = "#result.id")
	public ItemDto create(ItemDto dto) {
		return ItemDto.fromEntity(itemRepository.save(Item.builder()
			.name(dto.getName())
			.description(dto.getDescription())
			.price(dto.getPrice())
			.build()
		));
	}

	@Cacheable(cacheNames = "itemAllCache", key = "methodName")
	public List<ItemDto> readAll() {
		return itemRepository.findAll()
			.stream()
			.map(ItemDto::fromEntity)
			.toList();
	}

	// cacheNames: 메서드로 인해서 만들어질 캐시를 지칭하는 이름
	// key: 캐시에서 데이터를 구분하기 위해 활용할 값
	@Cacheable(cacheNames = "itemCache", key = "args[0]")
	public ItemDto readOne(Long id) {
		log.info("Read One: {}", id);
		return itemRepository.findById(id)
			.map(ItemDto::fromEntity)
			.orElseThrow(()
				-> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@CachePut(cacheNames = "itemCache", key = "args[0]")
	@CacheEvict(cacheNames = "itemAllCache", allEntries = true)
	public ItemDto update(Long id, ItemDto dto) {
		Item item = itemRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		item.setName(dto.getName());
		item.setDescription(dto.getDescription());
		item.setPrice(dto.getPrice());
		return ItemDto.fromEntity(itemRepository.save(item));
	}

	@Caching(evict = {
		@CacheEvict(cacheNames = "itemCache", key = "#id"),
		@CacheEvict(cacheNames = "itemAllCache", allEntries = true)
	})
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

	@Cacheable(
		cacheNames = "itemSearchCache",
		key = "{ #query, #pageable.pageNumber, #pageable.pageSize }"
	)
	public Page<ItemDto> searchByName(String query, Pageable pageable) {
		return itemRepository.findAllByNameContains(query, pageable)
			.map(ItemDto::fromEntity);
	}
}
