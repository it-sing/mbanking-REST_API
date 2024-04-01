package co.istad.mbanking.features.user;

import co.istad.mbanking.domain.Role;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.user.dto.UserCreateRequest;
import co.istad.mbanking.features.user.dto.UserPasswordRequest;
import co.istad.mbanking.features.user.dto.UserUpdateRequest;
import co.istad.mbanking.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;


    @Override
    public void UpdateUser(UserUpdateRequest userUpdateProfileRequest, String uuid) {
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
    public void createNew(UserCreateRequest request) {
        User user = userMapper.fromUserCreateRequest(request);

        if(userRepository.existsByNationalCardId(request.nationalCardId())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "National card id is already existed!"
            );
        }

        if(userRepository.existsByPhoneNumber(request.phoneNumber())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Phone number is already existed!"
            );
        }

        if(userRepository.existsByStudentIdCard(request.studentIdCard())){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Student card id is already existed!"
            );
        }

        if(!request.password().equals(request.confirmedPassword())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password does not match"
            );
        }


        List<Role> roleList = new ArrayList<>();
        Role role = roleRepository.findByName("USER")
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User role does not exist!"
                ));

        roleList.add(role);

        user.setUuid(UUID.randomUUID().toString());
        user.setProfileImage("Avatar.png");
        user.setCreatedAt(LocalDateTime.now());
        user.setIsBlocked(false);
        user.setIsDeleted(false);
        user.setRoles(roleList);

        userRepository.save(user);
    }

}
