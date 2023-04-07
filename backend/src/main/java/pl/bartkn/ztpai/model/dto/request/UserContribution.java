package pl.bartkn.ztpai.model.dto.request;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserContribution {
    private Long userId;
    private BigDecimal contribution;
}
