package com.ramesh.repository;

import com.ramesh.domain.Option;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface OptionRepository extends PagingAndSortingRepository<Option, Long> {
}
