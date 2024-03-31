package co.istad.mbanking.features.user;

import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.user.dto.UserCreateRequest;
import co.istad.mbanking.features.user.dto.UserPasswordRequest;

public interface UserService {

    void createNew(UserCreateRequest userCreateRequest);
    void changePassword(UserPasswordRequest userPasswordRequest, String Uuid);

}
