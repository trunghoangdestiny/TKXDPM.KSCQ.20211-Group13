package eco.bike.rental.entity.bike;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseBike {
    private String name;
    private String codeBike;
    private String bikeNumber;
    private Float bikePrice;
    private static final Float DEPOSIT_PERCENT = 0.4f;

    public Float getDepositPrice() {
        return bikePrice * DEPOSIT_PERCENT;
    }
}
