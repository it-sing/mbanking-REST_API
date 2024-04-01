package co.istad.mbanking.mapper;

import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.domain.CardType;
import co.istad.mbanking.features.accountType.dto.AccountTypeResponse;
import co.istad.mbanking.features.cardType.dto.CardTypeResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardTypeMapper{
    List<CardTypeResponse> toListCardTypesResponse(List<CardType> cardTypes);
    CardTypeResponse toCardTypeResponse(CardType cardType);
}
