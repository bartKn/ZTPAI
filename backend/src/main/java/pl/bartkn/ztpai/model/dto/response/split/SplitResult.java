package pl.bartkn.ztpai.model.dto.response.split;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.bartkn.ztpai.model.dto.response.user.UserInfo;

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
