package co.istad.mbanking.features.user;

import co.istad.mbanking.base.BasedMessage;
import co.istad.mbanking.domain.Role;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.user.dto.*;
import co.istad.mbanking.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
//use log
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;


    @Transactional
    @Override
    public void deleteByUuid(String uuid) {
        if (!userRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found");
        }
        userRepository.deleteByUuid(uuid);
    }
    @Transactional
    @Override
    public void enableByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setIsEnabled(true);
        userRepository.save(user);
    }
    @Transactional
    @Override
    public void disableByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setIsEnabled(false);
        userRepository.save(user);
    }
    @Override
    public Page<UserResponse> findList(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<User> users = userRepository.findAll(pageRequest);
        return users.map(userMapper::toUserResponse);
    }
    @Override
    public UserResponse updateByUuid(String uuid, UserUpdateRequest userUpdateRequest) {

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "User has not been found!"));

        userMapper.fromUserUpdateRequest(userUpdateRequest, user);
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }



    @Override
    public void updateUserProfile(UserUpdateProfileRequest userUpdateProfileRequest, String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setCityOrProvince(userUpdateProfileRequest.newCityOrProvince());
        user.setKhanOrDistrict(userUpdateProfileRequest.newKhanOrDistrict());
        user.setSangKatOrCommune(userUpdateProfileRequest.newSangKatOrCommune());
        user.setVillage(userUpdateProfileRequest.newVillage());
        user.setStreet(userUpdateProfileRequest.newStreet());
        user.setEmployeeType(userUpdateProfileRequest.newEmployeeType());
        user.setPosition(userUpdateProfileRequest.newPosition());
        user.setCompanyName(userUpdateProfileRequest.newCompanyName());
        user.setMainSourceOfIncome(userUpdateProfileRequest.newMainSourceOfIncome());
        user.setMonthlyIncomeRange(userUpdateProfileRequest.newMonthlyIncomeRange());

        userRepository.save(user);
    }

    @Override
    public void changePassword(UserPasswordRequest userPasswordRequest, String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!userPasswordRequest.oldPassword().equals(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }

        if (!userPasswordRequest.newPassword().equals(userPasswordRequest.newConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password and confirmed password do not match");
        }

        user.setPassword(userPasswordRequest.newPassword());

        userRepository.save(user);
    }
    @Override
    public void createNew(UserCreateRequest userCreateRequest) {
        User user = userMapper.fromUserCreateRequest(userCreateRequest);

        if(userRepository.existsByNationalCardId(userCreateRequest.nationalCardId())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "National card id is already existed!"
            );
        }

        if(userRepository.existsByPhoneNumber(userCreateRequest.phoneNumber())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Phone number is already existed!"
            );
        }

        if(userRepository.existsByStudentIdCard(userCreateRequest.studentIdCard())){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Student card id is already existed!"
            );
        }

        if(!userCreateRequest.password().equals(userCreateRequest.confirmedPassword())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match"
            );
        }

        user.setUuid(UUID.randomUUID().toString());
        user.setProfileImage("Avatar.png");
        user.setCreatedAt(LocalDateTime.now());
        user.setIsBlocked(false);
        user.setIsDeleted(false);
        user.setIsEnabled(false);

        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Role USER has not been found!"));

        userCreateRequest.roles().forEach(r -> {
            Role newRole = roleRepository.findByName(r.name())
                    .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Role USER has not been found!"));
            roles.add(newRole);
        });


        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

    }

    @Override
    public UserDetailsResponse findByUuid(String uuid) {

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "User has not been found!"));

        return userMapper.toUserDetailsResponse(user);
    }

    @Transactional
    @Override
    public BasedMessage blockByUuid(String uuid) {
        if (!userRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User has not been found!");
        }
        userRepository.blockByUuid(uuid);
        return new BasedMessage("User has been blocked");
    }

}
