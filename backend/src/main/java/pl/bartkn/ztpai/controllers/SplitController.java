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

    @PostMapping("/split/new")
    public ResponseEntity<?> createSplit(@RequestBody UserContributionWrapper userContributions) {
        System.out.println(userContributions);
        splitService.createSplit(userContributions.getUserContribution());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/split")
    public ResponseEntity<?> createSplit(@RequestBody UserContribution userContributions) {
        System.out.println(userContributions);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/split/addUser")
    public void addToSplit(UserContribution userContribution, Long splitId) {
        splitService.addUserToSplit(userContribution, splitId);
    }

    @GetMapping("/split/result/{splitId}")
    public ResponseEntity<SplitResults> getSplitResult(@PathVariable Long splitId) {
        return ResponseEntity.ok().body(splitService.calculateResult(splitId));
    }
}
