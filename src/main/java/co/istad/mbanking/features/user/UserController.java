package co.istad.mbanking.features.user;

import co.istad.mbanking.features.user.dto.UserCreateRequest;
import co.istad.mbanking.features.user.dto.UserPasswordRequest;
import co.istad.mbanking.features.user.dto.UserUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        userService.createNew(userCreateRequest);
    }
    @PatchMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable String uuid,
                               @RequestBody UserPasswordRequest userPasswordRequest) {
        userService.changePassword(userPasswordRequest,uuid);
    }
    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editUser(@PathVariable String uuid,
                         @RequestBody UserUpdateRequest userUpdateProfileRequest) {
        userService.UpdateUser(userUpdateProfileRequest , uuid);
    }

}

