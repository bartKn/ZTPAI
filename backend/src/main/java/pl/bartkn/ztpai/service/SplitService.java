package pl.bartkn.ztpai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bartkn.ztpai.model.dto.request.UserContribution;
import pl.bartkn.ztpai.model.dto.response.SplitResults;
import pl.bartkn.ztpai.model.entity.Split;
import pl.bartkn.ztpai.model.entity.User;
import pl.bartkn.ztpai.repository.SplitRepository;
import pl.bartkn.ztpai.repository.UserRepository;
import pl.bartkn.ztpai.util.SplitCalculator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SplitService {
    private final SplitRepository splitRepository;
    private final UserRepository userRepository;
    private final SplitCalculator splitCalculator;

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
}
