package pl.bartkn.ztpai.model.dto.request;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SplitCalcRequest {
    private Long id;
    private BigDecimal balance;
    private String username;
}
