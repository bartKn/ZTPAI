package pl.bartkn.ztpai.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.bartkn.ztpai.model.dto.request.UserContribution;
import pl.bartkn.ztpai.model.dto.request.UserContributionWrapper;
import pl.bartkn.ztpai.model.dto.response.SimpleSplitData;
import pl.bartkn.ztpai.model.dto.response.SplitData;
import pl.bartkn.ztpai.model.dto.response.SplitResults;
import pl.bartkn.ztpai.model.entity.User;
import pl.bartkn.ztpai.service.SplitService;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/api/splits")
    public ResponseEntity<Map<Long, List<SplitData>>> getSplitsOfUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(splitService.getSplitDataForUser(user));
    }

    @GetMapping("/api/splits/data")
    public ResponseEntity<List<SimpleSplitData>> getSimpleSplitData() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(splitService.getSplitData(user));
    }

    @DeleteMapping("/api/splits/delete/{splitId}")
    public void deleteSplitById(@PathVariable Long splitId) {
        splitService.deleteSplit(splitId);
    }
}
