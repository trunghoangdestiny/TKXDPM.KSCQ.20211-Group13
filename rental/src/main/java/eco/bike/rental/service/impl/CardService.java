package eco.bike.rental.service.impl;

import eco.bike.rental.entity.Card;
import eco.bike.rental.repository.ICardRepository;
import eco.bike.rental.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService implements ICardService {
    @Autowired
    private ICardRepository cardRepository;

    @Override
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Card getByCardNumber(String cardNumber) {
        return cardRepository.getByCardNumber(cardNumber);
    }
}
