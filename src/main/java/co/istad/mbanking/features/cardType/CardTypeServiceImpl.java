package co.istad.mbanking.features.cardType;

import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.domain.CardType;
import co.istad.mbanking.features.accountType.dto.AccountTypeResponse;
import co.istad.mbanking.features.cardType.dto.CardTypeResponse;
import co.istad.mbanking.mapper.CardTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardTypeServiceImpl implements CardTypeService{

    private final CardTypeRepository cardTypeRepository;
    private final CardTypeMapper cardTypeMapper;
    @Override
    public List<CardTypeResponse> findList() {
        List<CardType> cardTypes = cardTypeRepository.findAll();
        return cardTypeMapper.toListCardTypesResponse(cardTypes);
    }
    @Override
    public CardTypeResponse findByName(String name) {
        CardType cardType = cardTypeRepository.findByName(name).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Name does not exits"
                )
        );
        return cardTypeMapper.toCardTypeResponse(cardType);
    }
}
