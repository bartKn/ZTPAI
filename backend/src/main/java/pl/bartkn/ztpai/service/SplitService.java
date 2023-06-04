package pl.bartkn.ztpai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bartkn.ztpai.model.dto.request.split.SplitCalcRequest;
import pl.bartkn.ztpai.model.dto.request.split.SplitCalcRequestList;
import pl.bartkn.ztpai.model.dto.request.split.UserContributionUpdate;
import pl.bartkn.ztpai.model.dto.response.split.*;
import pl.bartkn.ztpai.model.entity.Split;
import pl.bartkn.ztpai.model.entity.User;
import pl.bartkn.ztpai.model.mapper.SplitDataMapper;
import pl.bartkn.ztpai.repository.SplitRepository;
import pl.bartkn.ztpai.repository.UserRepository;
import pl.bartkn.ztpai.util.SplitCalculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SplitService {
    private final SplitRepository splitRepository;
    private final UserRepository userRepository;
    private final SplitCalculator splitCalculator;

    private final SplitDataMapper dataMapper;

    public Long createSplit(List<Long> userIds, Long creatorId) {
        Split split = new Split();
        Map<User, BigDecimal> usersContributions = new HashMap<>();
        usersContributions.put(
                userRepository.getReferenceById(creatorId),
                BigDecimal.valueOf(-1));
        for (Long userId : userIds) {
            usersContributions.put(
                    userRepository.getReferenceById(userId),
                    BigDecimal.valueOf(-1)
            );
        }

        split.setUsersContributions(usersContributions);
        split.setFinished(false);
        splitRepository.save(split);
        return split.getId();
    }

    public SplitDetails getSplit(Long splitId) {
        Split s = splitRepository.getReferenceById(splitId);
        return SplitDetails.builder()
                .splitId(s.getId())
                .finished(s.isFinished())
                .contributions(s.getUsersContributions().entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                e -> e.getKey().getUsername(),
                                Map.Entry::getValue
                        )))
                .build();
    }

    public void updateUserContribution(UserContributionUpdate userContributionUpdate) {
        var split = splitRepository.getReferenceById(userContributionUpdate.getSplitId());
        split.getUsersContributions()
                .put(
                        userRepository.getUserByEmail(userContributionUpdate.getEmail()),
                        userContributionUpdate.getContribution()
                );
        splitRepository.save(split);
        tryToCalculate(split);
    }

    public SplitResults calculateResult(Long splitId) {
        Split split = splitRepository.getReferenceById(splitId);
        return splitCalculator.splitResults(split.getUsersContributions());
    }

    public Map<Long, List<SplitData>> getSplitDataForUser(User user) {
        var result = splitRepository.findByUserId(user.getId());
        return result.stream()
                .collect(Collectors.groupingBy(ISplitData::getSplitId,
                        Collectors.mapping(dataMapper::map, Collectors.toList())));
    }

    public List<SimpleSplitData> getSplitData(User user) {
        var result = splitRepository.findDataByUserId(user.getId());
        List<SimpleSplitData> results = new ArrayList<>();
        for (ISimpleSplitData s : result) {
            results.add(new SimpleSplitData(s.getSplitId(), s.isFinished()));
        }
        return results;
    }

    public void deleteSplit(Long splitId) {
        splitRepository.deleteById(splitId);
    }

    public SplitResults handleCalculateRequest(SplitCalcRequestList splitData) {
        Map<User, BigDecimal> contributions = new HashMap<>();
        for (SplitCalcRequest request : splitData.getSplitDataList()) {
            contributions.put(
                    User.builder()
                            .id(request.getId())
                            .username(request.getUsername())
                            .email(request.getUsername())
                            .build(),
                    request.getBalance()
            );
        }
        return splitCalculator.splitResults(contributions);
    }

    private void tryToCalculate(Split split) {
        boolean isReadyToCalculate = split.getUsersContributions()
                .values().stream()
                .allMatch(x -> x.compareTo(BigDecimal.valueOf(-1)) > 0);
        if (isReadyToCalculate) {
            split.setFinished(true);
            splitRepository.save(split);
            resolveSplit(split.getId());
        }
    }

    private void resolveSplit(Long splitId) {
        SplitResults results = calculateResult(splitId);
        for (SplitResult result : results.getResults()) {
            User from = userRepository.getReferenceById(result.getFrom().getId());
            User to = userRepository.getReferenceById(result.getFrom().getId());
            BigDecimal amount = result.getAmount();
            from.setBalance(from.getBalance().subtract(amount));
            userRepository.save(from);
            to.setBalance(to.getBalance().add(amount));
            userRepository.save(to);
        }
    }
}
