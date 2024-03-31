package co.istad.mbanking.features.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserPasswordRequest(
        @NotBlank
        String oldPassword,
        @NotBlank
        String newPassword,
        @NotBlank
        String confirmPassword
) {


}
