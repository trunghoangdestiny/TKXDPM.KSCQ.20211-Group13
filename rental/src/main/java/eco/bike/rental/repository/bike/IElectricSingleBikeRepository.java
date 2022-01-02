package eco.bike.rental.repository.bike;

import eco.bike.rental.entity.bike.ElectricSingleBike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IElectricSingleBikeRepository extends JpaRepository<ElectricSingleBike, Long>, IBaseBikeRepository<ElectricSingleBike> {
}
