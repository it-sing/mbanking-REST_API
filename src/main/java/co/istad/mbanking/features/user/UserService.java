package co.istad.mbanking.features.user;

import co.istad.mbanking.base.BasedMessage;
import co.istad.mbanking.features.user.dto.*;
import org.springframework.data.domain.Page;

public interface UserService {

    void createNew(UserCreateRequest userCreateRequest);
    void changePassword(UserPasswordRequest userPasswordRequest, String uuid);
    void updateUserProfile(UserUpdateProfileRequest userUpdateProfileRequest , String uuid);
    UserResponse updateByUuid(String uuid, UserUpdateRequest userUpdateRequest);
    UserDetailsResponse findByUuid(String uuid);
    BasedMessage blockByUuid(String uuid);
    void deleteByUuid(String uuid );
    void enableByUuid(String uuid);
    void disableByUuid(String uuid);
    String updateProfileImage(String uuid ,String mediaName);

    Page<UserResponse> findList(int page , int limit);
}
