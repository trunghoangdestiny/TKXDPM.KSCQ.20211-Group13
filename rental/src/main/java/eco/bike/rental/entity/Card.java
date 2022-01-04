package eco.bike.rental.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String owner;
    private String cardNumber;
    private String issuingBank;
    private String expirationDate;
    private String cvvCode;

    private Boolean isAvailable; // check if card is using

    @OneToMany(mappedBy = "card")
    private List<OrderHistory> orderHistory;

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", issuingBank='" + issuingBank + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", cvvCode='" + cvvCode + '\'' +
                '}';
    }
}
