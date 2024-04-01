package co.istad.mbanking.features.user.dto;

public record UserResponse(
        String uuid,
        String name,
        String ProfileImage,
        String gender,
        String dob
        ) {
}
