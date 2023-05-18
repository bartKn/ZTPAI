package pl.bartkn.ztpai.model.dto.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SplitData {
    private String username;
    private Long userId;
    private BigDecimal contribution;
    private Long splitId;
    private boolean finished;
}
