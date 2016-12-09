package com.ramesh.repository;

import com.ramesh.domain.Poll;

import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Long> {
}
