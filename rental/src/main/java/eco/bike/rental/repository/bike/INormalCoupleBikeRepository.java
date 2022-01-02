package eco.bike.rental.repository.bike;

import eco.bike.rental.entity.bike.NormalCoupleBike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INormalCoupleBikeRepository extends JpaRepository<NormalCoupleBike, Long>, IBaseBikeRepository<NormalCoupleBike> {
}
