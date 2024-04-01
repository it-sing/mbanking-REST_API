package co.istad.mbanking.features.cardType;

import co.istad.mbanking.features.cardType.dto.CardTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/card-types")
@RequiredArgsConstructor
public class CardTypeController {
    private final CardTypeService cardTypeService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CardTypeResponse> findList(){
        return cardTypeService.findList();
    }
    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    CardTypeResponse findByName( @PathVariable String name){
        return cardTypeService.findByName(name);
    }
}
