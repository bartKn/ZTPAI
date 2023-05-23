package pl.bartkn.ztpai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bartkn.ztpai.model.dto.request.UserContribution;
import pl.bartkn.ztpai.model.dto.response.*;
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

    public void createSplit(List<UserContribution> contributions) {
        Split split = new Split();
        Map<User, BigDecimal> usersContributions = new HashMap<>();
        for (UserContribution contribution : contributions) {
            usersContributions.put(
                    userRepository.getReferenceById(contribution.getUserId()),
                    contribution.getContribution()
            );
        }

        split.setUsersContributions(usersContributions);
        split.setFinished(false);
        splitRepository.save(split);
    }

    public void addUserToSplit(UserContribution userContribution, Long splitId) {
        var split = splitRepository.getReferenceById(splitId);
        split.getUsersContributions()
                .put(
                        userRepository.getReferenceById(userContribution.getUserId()),
                        userContribution.getContribution()
                );
        splitRepository.save(split);

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
}
