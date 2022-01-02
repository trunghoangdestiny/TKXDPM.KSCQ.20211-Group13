package eco.bike.rental.entity.bike;

import eco.bike.rental.entity.BikeParking;
import eco.bike.rental.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ElectricSingleBike extends BaseBike{
    private Long currentBattery;
    private Long estimatedTime;

    @ManyToOne
    @JoinColumn(name = "bike_parking_id", nullable = false)
    private BikeParking bikeParking;
}
