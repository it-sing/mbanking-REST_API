package co.istad.mbanking.features.user;

import co.istad.mbanking.domain.Role;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.user.dto.UserCreateRequest;
import co.istad.mbanking.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
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
