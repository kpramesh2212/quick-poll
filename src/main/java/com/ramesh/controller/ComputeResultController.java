package com.ramesh.controller;

import com.ramesh.domain.Vote;
import com.ramesh.dto.OptionCount;
import com.ramesh.dto.VoteResult;
import com.ramesh.repository.VoteRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/computeresult")
@Api(value = "Compute Poll Results", description = "Compute Result API")
public class ComputeResultController {
    @Inject private VoteRepository voteRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Compute results for associated poll id", response = VoteResult.class)
    public ResponseEntity<VoteResult> computeResult(@RequestParam Long pollId) {
        VoteResult voteResult = new VoteResult();
        int totalVotes = 0;
        Iterable<Vote> allvotes = voteRepository.findByPoll(pollId);
        Map<Long, OptionCount> optionCountMap = new HashMap<>();

        for (Vote vote : allvotes) {
            ++totalVotes;
            final Long optionId = vote.getOption().getId();
            OptionCount optionCount = new OptionCount();
            if (optionCountMap.containsKey(optionId)) {
                optionCount = optionCountMap.get(optionId);
            } else {
                optionCount.setOptionId(optionId);
            }
            optionCount.setCount(optionCount.getCount() + 1);
            optionCountMap.put(optionId, optionCount);

        }
        voteResult.setTotalVotes(totalVotes);
        voteResult.setResults(optionCountMap.values());
        return new ResponseEntity<>(voteResult, HttpStatus.OK);
    }


}
