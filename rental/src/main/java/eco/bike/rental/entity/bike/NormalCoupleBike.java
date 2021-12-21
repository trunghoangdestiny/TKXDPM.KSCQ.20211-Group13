package eco.bike.rental.entity.bike;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NormalCoupleBike extends BaseBike{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
