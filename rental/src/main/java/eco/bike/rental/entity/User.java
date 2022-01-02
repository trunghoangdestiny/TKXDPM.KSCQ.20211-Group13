package eco.bike.rental.entity;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<ElectricSingleBike> electricSingleBikes;

    @OneToMany(mappedBy = "user")
    private List<NormalSingleBike> normalSingleBikes;

    @OneToMany(mappedBy = "user")
    private List<NormalCoupleBike> normalCoupleBikes;
}
