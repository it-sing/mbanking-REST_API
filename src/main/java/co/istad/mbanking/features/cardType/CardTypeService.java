package co.istad.mbanking.features.cardType;

import co.istad.mbanking.features.accountType.dto.AccountTypeResponse;
import co.istad.mbanking.features.cardType.dto.CardTypeResponse;

import java.util.List;

public interface CardTypeService {
    List<CardTypeResponse> findList();
    CardTypeResponse findByName(String name);
}
