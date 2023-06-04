package pl.bartkn.ztpai.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.bartkn.ztpai.model.dto.request.user.BalanceUpdate;
import pl.bartkn.ztpai.model.dto.request.user.DataUpdate;
import pl.bartkn.ztpai.model.dto.request.user.UserId;
import pl.bartkn.ztpai.model.dto.response.user.UserAccountDetails;
import pl.bartkn.ztpai.model.dto.response.user.UserInfo;
import pl.bartkn.ztpai.model.entity.User;
import pl.bartkn.ztpai.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/details")
    public UserAccountDetails getUserDetails() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUserDetails(user);
    }

    @PatchMapping("/username")
    public UserAccountDetails updateUsername(@RequestBody DataUpdate username) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Username update");
        return userService.updateUsername(user, username.getValue());
    }

    @PatchMapping("/balance")
    public UserAccountDetails updateUsername(@RequestBody BalanceUpdate balance) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Balance update");
        return userService.updateBalance(user, balance.getValue());
    }

    @GetMapping("/all")
    public List<UserInfo> getUsers(@RequestParam Optional<String> username) {
        return userService.getAllUsers(username);
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/friends")
    public List<UserInfo> getFriends() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserFriends(email);
    }

    @PostMapping("/friends/add")
    public ResponseEntity<?> addFriend(@RequestBody @Valid UserId id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.addFriend(email, id.getId());
        return ResponseEntity.ok().build();
    }
}
