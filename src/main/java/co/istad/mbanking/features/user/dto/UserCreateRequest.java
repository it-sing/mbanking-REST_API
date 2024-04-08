
package co.istad.mbanking.features.user.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;


public record UserCreateRequest(
        @NotNull(message = "Pin is required")
        @Max(9999)
        @Positive
        Integer pin,

        @NotBlank(message = "Phone number is required")
        @Size(max = 20)
        String phoneNumber,

        @NotBlank (message = "Email is required")
        String password,

        @NotBlank(message = "Confirm password is required")
        String confirmedPassword,

        @NotBlank (message = "Name is required")
        @Size(max = 40)
        String name,

        @NotBlank(message = "gender is required")
        @Size(max = 6)
        String gender,

        @NotNull (message = "Date of birth is required")
        LocalDate dob,

        @NotBlank(message = "National card id is required")
        @Size(max = 20)
        String nationalCardId,

        @Size(max = 20)
        String studentIdCard,
        @NotEmpty
        List<RoleRequest> roles
) {
}

