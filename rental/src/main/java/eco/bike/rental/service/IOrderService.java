package eco.bike.rental.service;

import eco.bike.rental.entity.OrderHistory;
import eco.bike.rental.entity.bike.BaseBike;

import java.util.Map;

public interface IOrderService {
    OrderHistory save(OrderHistory orderHistory);
    Map<OrderHistory, BaseBike> getAllOrder();
    OrderHistory getOrderById(Long id);
}
