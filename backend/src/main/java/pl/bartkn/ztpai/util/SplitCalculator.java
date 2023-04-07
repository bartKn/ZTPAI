package pl.bartkn.ztpai.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bartkn.ztpai.model.dto.response.SplitResult;
import pl.bartkn.ztpai.model.dto.response.SplitResults;
import pl.bartkn.ztpai.model.entity.User;
import pl.bartkn.ztpai.model.mapper.UserMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SplitCalculator {

    private final UserMapper mapper;
    public SplitResults splitResults(Map<User, BigDecimal> contributions) {

        List<SplitResult> results = new ArrayList<>();

        contributions = MapUtil.sortAscending(contributions);

        BigDecimal totalValue = contributions.values()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal mean = totalValue.divide(BigDecimal.valueOf(contributions.keySet().size()), 2, RoundingMode.HALF_UP);

        Map<User, BigDecimal> debts = new LinkedHashMap<>();
        contributions.forEach((k, v) -> debts.put(k, v.subtract(mean)));
        Map<User, BigDecimal> tmp = new LinkedHashMap<>(debts);

        debts.forEach((key, value) -> System.out.println(key + " = " + value));

        for (Map.Entry<User, BigDecimal> entry : debts.entrySet()) {
            BigDecimal debt = entry.getValue();
            if (debt.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }

            while (debt.compareTo(BigDecimal.ZERO) < 0) {
                User top = MapUtil.getKeyOfTopValue(tmp);
                BigDecimal topValue = tmp.get(top);
                System.out.println("Top - " + top + " - value = " + topValue);
                if (topValue.add(debt).compareTo(BigDecimal.ZERO) > 0 ||
                    topValue.add(debt).compareTo(BigDecimal.ZERO) == 0) {

                    results.add(new SplitResult(
                            mapper.map(entry.getKey()),
                            mapper.map(top),
                            debt.negate()
                    ));
                    tmp.put(top, topValue.add(debt));
                    debt = BigDecimal.ZERO;
                } else {
                    BigDecimal debtPart = tmp.get(top);
                    results.add(new SplitResult(
                            mapper.map(entry.getKey()),
                            mapper.map(top),
                            debtPart
                    ));
                    debt = debt.add(debtPart);
                    tmp.remove(top);
                }
            }
        }

        return new SplitResults(results);
    }
}
