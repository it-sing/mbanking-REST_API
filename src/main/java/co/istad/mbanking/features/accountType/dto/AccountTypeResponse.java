package co.istad.mbanking.features.accountType.dto;

public record AccountTypeResponse(
        String alias,
        String name,
        String description
) {
}
