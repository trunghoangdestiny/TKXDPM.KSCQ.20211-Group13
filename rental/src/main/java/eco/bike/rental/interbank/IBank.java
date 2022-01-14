package eco.bike.rental.interbank;

import eco.bike.rental.entity.Card;

public interface IBank<T> {
    T payOrder(Card card, Float amount, String transactionContent);
}
