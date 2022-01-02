package eco.bike.rental.repository.bike;

import eco.bike.rental.entity.bike.NormalSingleBike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INormalSingleBikeRepository extends JpaRepository<NormalSingleBike, Long>, IBaseBikeRepository<NormalSingleBike> {
}
