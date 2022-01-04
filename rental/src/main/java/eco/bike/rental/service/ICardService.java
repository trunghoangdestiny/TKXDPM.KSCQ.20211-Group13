package eco.bike.rental.service;

import eco.bike.rental.entity.Card;

public interface ICardService {
    Card save(Card card);
    Card getByCardNumber(String cardNumber);
}
