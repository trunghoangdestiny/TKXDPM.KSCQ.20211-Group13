package eco.bike.rental.repository;

import eco.bike.rental.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardRepository extends JpaRepository<Card, Long> {
    Card getByCardNumber(String cardNumber);
}
