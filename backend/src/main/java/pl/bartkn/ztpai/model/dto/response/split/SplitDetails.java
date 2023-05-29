package pl.bartkn.ztpai.model.dto.response.split;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SplitDetails {
    private Long splitId;
    private Map<String, BigDecimal> contributions;
    private boolean finished;
}
