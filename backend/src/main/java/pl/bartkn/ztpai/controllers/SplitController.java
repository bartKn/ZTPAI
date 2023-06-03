package pl.bartkn.ztpai.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.bartkn.ztpai.model.dto.request.SplitCalcRequestList;
import pl.bartkn.ztpai.model.dto.request.UserContributionUpdate;
import pl.bartkn.ztpai.model.dto.request.UserIdList;
import pl.bartkn.ztpai.model.dto.response.split.SimpleSplitData;
import pl.bartkn.ztpai.model.dto.response.split.SplitData;
import pl.bartkn.ztpai.model.dto.response.split.SplitDetails;
import pl.bartkn.ztpai.model.dto.response.split.SplitResults;
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
    public ResponseEntity<Long> createSplit(@RequestBody UserIdList usersIds) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long creatorId = user.getId();
        return ResponseEntity.ok().body(splitService.createSplit(usersIds.getUserIds(), creatorId));
    }

    @PostMapping("/update")
    public void addToSplit(@RequestBody UserContributionUpdate userContribution) {
        splitService.updateUserContribution(userContribution);
    }

    @GetMapping("/result/{splitId}")
    public ResponseEntity<SplitResults> getSplitResult(@PathVariable Long splitId) {
        return ResponseEntity.ok().body(splitService.calculateResult(splitId));
    }

    @GetMapping("/{splitId}")
    public ResponseEntity<SplitDetails> getSplitById(@PathVariable Long splitId) {
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

    @PostMapping("/calculate")
    public SplitResults calculateResults(@RequestBody SplitCalcRequestList splitData) {
        return splitService.handleCalculateRequest(splitData);
    }
}
