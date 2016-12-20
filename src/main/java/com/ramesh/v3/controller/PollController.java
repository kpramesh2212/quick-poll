package com.ramesh.v3.controller;

import com.ramesh.domain.Poll;
import com.ramesh.exception.ResourceNotFoundException;
import com.ramesh.repository.PollRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController("PollControllerV3")
@RequestMapping(value = "/v3/polls")
@Api(value = "polls", description = "Poll API")
public class PollController {

    @Inject
    private PollRepository pollRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all the polls", response = Poll.class,
            responseContainer="List")
    public ResponseEntity<Page<Poll>> getAllPolls(Pageable pageable) {
        Page<Poll> allPolls = pollRepository.findAll(pageable);
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @RequestMapping(value = "/{pollId}", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves a Poll associated with the pollId", response = Poll.class)
    public ResponseEntity<Poll> getPoll(@PathVariable Long pollId) {
        Poll poll = verifyAndGetPoll(pollId);
        return new ResponseEntity<>(poll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new poll", response = Void.class)
    public ResponseEntity<Void> createPoll(@Valid @RequestBody Poll poll) {
        poll  = pollRepository.save(poll);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI pollUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(poll.getId()).toUri();
        responseHeaders.setLocation(pollUri);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{pollId}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates a Poll associated with pollId", response = Void.class)
    public ResponseEntity<Void> updatePoll(@Valid @RequestBody Poll poll, @PathVariable Long
            pollId) {
        verifyAndGetPoll(pollId);
        pollRepository.save(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "{pollId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes a Poll associated with pollId", response = Void.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePoll(@PathVariable Long pollId) {
        verifyAndGetPoll(pollId);
        pollRepository.delete(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected Poll verifyAndGetPoll(Long pollId) {
        Poll poll = pollRepository.findOne(pollId);
        if (poll == null) {
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        }
        return poll;
    }

}
