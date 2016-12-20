package com.ramesh.v3.controller;

import com.ramesh.domain.Vote;
import com.ramesh.repository.VoteRepository;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController("VoteControllerV3")
@RequestMapping(value = "/v3/polls/{pollId}/votes")
@Api(value = "votes", description = "Vote API")
public class VoteController {

    @Inject
    private VoteRepository voteRepository;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new vote for associated poll Id", response = Void.class)
    public ResponseEntity<Void> createVote(@RequestBody Vote vote, @PathVariable Long pollId) {
        System.out.println(vote);

        vote = voteRepository.save(vote);

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(vote.getId()).toUri());
        return new ResponseEntity<>(null, responseHeader, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all votes for associated poll Id", response = Vote.class,
            responseContainer = "List")
    public ResponseEntity<Iterable<Vote>> getAllVotes(@PathVariable Long pollId) {
        return new ResponseEntity<>(voteRepository.findByPoll(pollId), HttpStatus.OK);
    }



}
