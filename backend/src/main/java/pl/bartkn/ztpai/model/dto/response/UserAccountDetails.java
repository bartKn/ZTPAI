package pl.bartkn.ztpai.model.dto.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAccountDetails extends UserInfo {
    private BigDecimal balance;

    @Builder
    public UserAccountDetails(Long id, String email, String username, BigDecimal balance) {
        super(id, email, username);
        this.balance = balance;
    }
}
