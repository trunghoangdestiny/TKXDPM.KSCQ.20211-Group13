package eco.bike.rental.entity.bike;

import eco.bike.rental.entity.BikeParking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NormalCoupleBike extends BaseBike{
    @ManyToOne
    @JoinColumn(name = "bike_parking_id", nullable = false)
    private BikeParking bikeParking;
}
