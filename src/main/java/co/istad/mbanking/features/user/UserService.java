package co.istad.mbanking.features.user;

import co.istad.mbanking.features.user.dto.UserCreateRequest;
import co.istad.mbanking.features.user.dto.UserPasswordRequest;
import co.istad.mbanking.features.user.dto.UserUpdateRequest;

public interface UserService {

    void createNew(UserCreateRequest userCreateRequest);
    void changePassword(UserPasswordRequest userPasswordRequest, String uuid);
    void UpdateUser(UserUpdateRequest userUpdateProfileRequest , String uuid);
}
