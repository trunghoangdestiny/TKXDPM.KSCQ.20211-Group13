package eco.bike.rental.entity.bike;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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

    public Float getDepositPrice() {
        return bikePrice * DEPOSIT_PERCENT;
    }
}
