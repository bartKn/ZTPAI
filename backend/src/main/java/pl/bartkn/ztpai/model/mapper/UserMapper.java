package pl.bartkn.ztpai.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.bartkn.ztpai.model.dto.request.auth.RegisterRequest;
import pl.bartkn.ztpai.model.dto.response.user.UserInfo;
import pl.bartkn.ztpai.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User map(RegisterRequest request);

    @Mapping(target = "username", expression = "java(user.getUsernameValue())")
    UserInfo map(User user);
}
