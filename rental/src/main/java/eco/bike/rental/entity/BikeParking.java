package eco.bike.rental.entity;

import eco.bike.rental.entity.bike.BaseBike;
import eco.bike.rental.entity.bike.ElectricSingleBike;
import eco.bike.rental.entity.bike.NormalCoupleBike;
import eco.bike.rental.entity.bike.NormalSingleBike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BikeParking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String address;
    private Integer area; // m2
    private Integer currentQuantity;

    @OneToMany(mappedBy = "bikeParking")
    private List<ElectricSingleBike> electricSingleBikes;

    @OneToMany(mappedBy = "bikeParking")
    private List<NormalSingleBike> normalSingleBikes;

    @OneToMany(mappedBy = "bikeParking")
    private List<NormalCoupleBike> normalCoupleBikes;

    @Override
    public String toString() {
        return "BikeParking{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", area=" + area +
                ", currentQuantity=" + currentQuantity +
                '}';
    }
}
