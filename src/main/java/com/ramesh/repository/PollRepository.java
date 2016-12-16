package com.ramesh.repository;

import com.ramesh.domain.Poll;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
}
