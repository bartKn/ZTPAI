package pl.bartkn.ztpai.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bartkn.ztpai.model.dto.request.UserContribution;
import pl.bartkn.ztpai.model.dto.request.UserContributionWrapper;
import pl.bartkn.ztpai.model.dto.response.SplitResults;
import pl.bartkn.ztpai.service.SplitService;

@RestController
@RequiredArgsConstructor
public class SplitController {

    private final SplitService splitService;

    @PostMapping("/api/split/new")
    public ResponseEntity<?> createSplit(@RequestBody UserContributionWrapper userContributions) {
        System.out.println(userContributions);
        splitService.createSplit(userContributions.getUserContribution());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/split/addUser")
    public void addToSplit(UserContribution userContribution, Long splitId) {
        splitService.addUserToSplit(userContribution, splitId);
    }

    @GetMapping("/api/split/result/{splitId}")
    public ResponseEntity<SplitResults> getSplitResult(@PathVariable Long splitId) {
        return ResponseEntity.ok().body(splitService.calculateResult(splitId));
    }
}
