package pl.bartkn.ztpai.model.dto.request.split;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserContributionUpdate {
    private String email;
    private BigDecimal contribution;
    private Long splitId;
}
