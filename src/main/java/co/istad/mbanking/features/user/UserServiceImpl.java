package co.istad.mbanking.features.user;

import co.istad.mbanking.domain.Role;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.user.dto.UserCreateRequest;
import co.istad.mbanking.features.user.dto.UserPasswordRequest;
import co.istad.mbanking.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;


    @Override
    public void changePassword(UserPasswordRequest userPasswordRequest, String uuid) {
        // Find the user based on the UUID
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Check if the old password matches the current password
        if (!userPasswordRequest.oldPassword().equals(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }

        // Check if the new password matches the confirmed password
        if (!userPasswordRequest.newPassword().equals(userPasswordRequest.confirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password and confirmed password do not match");
        }

        // Set the new password
        user.setPassword(userPasswordRequest.newPassword());
        user.setConfirmPassword(userPasswordRequest.confirmPassword());
        // Save the changes to the user
        userRepository.save(user);
    }
    @Override
    public void createNew(UserCreateRequest userCreateRequest) {

        if(userRepository.existsByPhoneNumber(userCreateRequest.phoneNumber())) {

            throw  new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "User with this phone number already exists"
            );
        }
        if(userRepository.existsByNationalCardId(userCreateRequest.nationalCardId())) {
            throw  new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "National card with this number already exists"
            );
        }
        if(userRepository.existsByStudentIdCard(userCreateRequest.studentIdCard())){
            throw  new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Student card with this number already exists"
            );
        }
        if(!userCreateRequest.password()
                .equals(userCreateRequest.confirmedPassword())){
            throw  new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Passwords do not match"
            );
        }

        User user = userMapper.fromUserCreateRequest(userCreateRequest);

        user.setUuid(UUID.randomUUID().toString());
        user.setProfileImage("avatar.png");
        user.setCreatedAt(LocalDateTime.now());
        user.setIsDeleted(false);
        user.setIsBlocked(false);

        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Role USER has not been found!"));
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

    }
}
