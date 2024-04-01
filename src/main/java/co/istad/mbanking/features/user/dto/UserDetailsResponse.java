package co.istad.mbanking.features.user.dto;

import co.istad.mbanking.domain.Role;

import java.math.BigDecimal;
import java.util.List;

public record UserDetailsResponse(
        String uuid,
        String name,
        String ProfileImage,
        String gender,
        String dob,
        String cityOrProvince,
        String khanOrDistrict,
        String sangKatOrCommune,
        String village,
        String street,
        String employeeType,
        String position,
        String mainSourceOfIncome,
        BigDecimal monthlyIncomeRange,
        String studentIdCard,
        List<RoleNameResponse> roles
) {

}
