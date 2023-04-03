package pl.bartkn.ztpai.model.mapper;

import org.mapstruct.Mapper;
import pl.bartkn.ztpai.model.dto.request.RegisterRequest;
import pl.bartkn.ztpai.model.dto.response.UserInfo;
import pl.bartkn.ztpai.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User map(RegisterRequest request);
    UserInfo map(User user);
}
