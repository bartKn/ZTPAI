package pl.bartkn.ztpai.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.bartkn.ztpai.model.dto.request.UserContribution;
import pl.bartkn.ztpai.model.dto.request.UserContributionWrapper;
import pl.bartkn.ztpai.model.dto.response.SimpleSplitData;
import pl.bartkn.ztpai.model.dto.response.SplitData;
import pl.bartkn.ztpai.model.dto.response.SplitDetails;
import pl.bartkn.ztpai.model.dto.response.SplitResults;
import pl.bartkn.ztpai.model.entity.User;
import pl.bartkn.ztpai.service.SplitService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/splits")
@RequiredArgsConstructor
public class SplitController {

    private final SplitService splitService;

    @PostMapping("/new")
    public ResponseEntity<?> createSplit(@RequestBody UserContributionWrapper userContributions) {
        System.out.println(userContributions);
        splitService.createSplit(userContributions.getUserContribution());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addUser")
    public void addToSplit(UserContribution userContribution, Long splitId) {
        splitService.addUserToSplit(userContribution, splitId);
    }

    @GetMapping("/result/{splitId}")
    public ResponseEntity<SplitResults> getSplitResult(@PathVariable Long splitId) {
        return ResponseEntity.ok().body(splitService.calculateResult(splitId));
    }

    @GetMapping("/{splitId}")
    public ResponseEntity<SplitDetails> getSplitById(@PathVariable Long splitId) {
        System.out.println("Get data about split with id: " + splitId);
        return ResponseEntity.ok().body(splitService.getSplit(splitId));
    }

    @GetMapping("/all")
    public ResponseEntity<Map<Long, List<SplitData>>> getSplitsOfUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(splitService.getSplitDataForUser(user));
    }

    @GetMapping("/data")
    public ResponseEntity<List<SimpleSplitData>> getSimpleSplitData() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(splitService.getSplitData(user));
    }

    @DeleteMapping("/delete/{splitId}")
    public void deleteSplitById(@PathVariable Long splitId) {
        splitService.deleteSplit(splitId);
    }
}
