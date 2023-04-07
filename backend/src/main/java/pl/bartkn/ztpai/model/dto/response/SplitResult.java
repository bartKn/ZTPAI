package pl.bartkn.ztpai.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SplitResult {
    private UserInfo from;
    private UserInfo to;
    private BigDecimal amount;
}
