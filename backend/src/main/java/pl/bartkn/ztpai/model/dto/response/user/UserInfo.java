package pl.bartkn.ztpai.model.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfo {
    protected Long id;
    protected String email;
    protected String username;
}
