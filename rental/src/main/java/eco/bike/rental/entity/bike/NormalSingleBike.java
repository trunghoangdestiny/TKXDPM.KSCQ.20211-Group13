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
public class NormalSingleBike extends BaseBike{
    @ManyToOne
    @JoinColumn(name = "bike_parking_id")
    private BikeParking bikeParking;
}
