package eco.bike.rental.repository;

import eco.bike.rental.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<OrderHistory, Long> {
}
