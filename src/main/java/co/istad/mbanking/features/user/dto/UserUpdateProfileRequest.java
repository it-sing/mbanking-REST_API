package co.istad.mbanking.features.user.dto;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public record UserUpdateProfileRequest(
        @Column(length = 100)
        String newCityOrProvince,
        @Column(length = 100)
        String newKhanOrDistrict,
        @Column(length = 100)
        String newSangKatOrCommune,
        @Column(length = 100)

        String newVillage,
        @Column(length = 100)
        String newStreet,
        @Column(length = 100)
        String newEmployeeType,
        @Column(length = 100)
        String newPosition,
        @Column(length = 100)
        String newCompanyName,
        @Column(length = 100)
        String newMainSourceOfIncome,
        @Column(length = 100)
        BigDecimal newMonthlyIncomeRange
) {}
