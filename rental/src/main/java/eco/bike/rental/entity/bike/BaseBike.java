package eco.bike.rental.entity.bike;

import eco.bike.rental.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseBike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String codeBike;
    private String bikeNumber;
    private Float bikePrice;

    private Boolean inUsed; // check if bike is using
    private static final Float DEPOSIT_PERCENT = 0.4f;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Float getDepositPrice() {
        return bikePrice * DEPOSIT_PERCENT;
    }
}
