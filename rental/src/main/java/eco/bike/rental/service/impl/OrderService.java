package eco.bike.rental.service.impl;

import eco.bike.rental.entity.OrderHistory;
import eco.bike.rental.entity.bike.BaseBike;
import eco.bike.rental.entity.bike.ElectricSingleBike;
import eco.bike.rental.entity.bike.NormalCoupleBike;
import eco.bike.rental.entity.bike.NormalSingleBike;
import eco.bike.rental.repository.IOrderRepository;
import eco.bike.rental.service.IBikeService;
import eco.bike.rental.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IBikeService<NormalSingleBike> normalSingleBikeService;

    @Autowired
    private IBikeService<NormalCoupleBike> normalCoupleBikeService;

    @Autowired
    private IBikeService<ElectricSingleBike> electricSingleBikeService;

    @Override
    public OrderHistory save(OrderHistory orderHistory) {
        return orderRepository.save(orderHistory);
    }

    @Override
    public Map<OrderHistory, BaseBike> getAllOrder() {
        Map<OrderHistory, BaseBike> orderMap = new HashMap<>();

        List<OrderHistory> orderHistoryList = orderRepository
                .findAll()
                .stream()
                .filter(orderHistory -> orderHistory.getIsSuccess() == true && orderHistory.getIsDone() == false)
                .collect(Collectors.toList());
        for (int i = 0; i < orderHistoryList.size(); i++) {
            orderHistoryList.get(i).setCurrentRentedTime(new Date());

            BaseBike bike = normalSingleBikeService
                    .getByIdAndBikeParkingId(
                            orderHistoryList.get(i).getBikeId(),
                            orderHistoryList.get(i).getBikeParkingStartId()
                    );
            if (bike != null) {
                orderMap.put(orderHistoryList.get(i), bike);
                continue;
            }

            bike = normalCoupleBikeService.getByIdAndBikeParkingId(
                    orderHistoryList.get(i).getBikeId(),
                    orderHistoryList.get(i).getBikeParkingStartId()
            );
            if (bike != null) {
                orderMap.put(orderHistoryList.get(i), bike);
                continue;
            }

            bike = electricSingleBikeService.getByIdAndBikeParkingId(
                    orderHistoryList.get(i).getBikeId(),
                    orderHistoryList.get(i).getBikeParkingStartId()
            );
            if (bike != null) {
                orderMap.put(orderHistoryList.get(i), bike);
            }
        }
        return orderMap;
    }
}
