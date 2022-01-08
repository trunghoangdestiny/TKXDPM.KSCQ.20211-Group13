package eco.bike.rental.service.impl;

import eco.bike.rental.entity.OrderHistory;
import eco.bike.rental.entity.bike.BaseBike;
import eco.bike.rental.entity.bike.ElectricSingleBike;
import eco.bike.rental.entity.bike.NormalCoupleBike;
import eco.bike.rental.entity.bike.NormalSingleBike;
import eco.bike.rental.repository.IOrderRepository;
import eco.bike.rental.service.IBikeService;
import eco.bike.rental.service.IOrderService;
import eco.bike.rental.calculator.CalculateFee;
import eco.bike.rental.calculator.NormalCalculateFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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

    private CalculateFee calculateFee = new CalculateFee(new NormalCalculateFee());

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

        String pattern = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        for (int i = 0; i < orderHistoryList.size(); i++) {
            //calculate time
            long usedTime = 0;
            try {
                Date startTime = simpleDateFormat.parse(orderHistoryList.get(i).getStartedAt().split(" ")[1]);
                String currentTimeString = simpleDateFormat.format(new Date());
                Date currentTime = simpleDateFormat.parse(currentTimeString);

                long diff = currentTime.getTime() - startTime.getTime();

                TimeUnit timeUnit = TimeUnit.SECONDS;
                usedTime = timeUnit.convert(diff, TimeUnit.MILLISECONDS); // time in seconds
                String currentRentedTime = usedTime / 3600 + "h " + (usedTime % 3600) / 60 + "m " + (usedTime % 60) + "s";

                orderHistoryList.get(i).setCurrentRentedTime(currentRentedTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //calculate fee
            orderHistoryList.get(i).setCurrentPrice(calculateFee.calculateFee(usedTime));

            //save to db
            orderRepository.save(orderHistoryList.get(i));

            BaseBike bike = normalSingleBikeService.getByCodeBike(orderHistoryList.get(i).getBikeCode());
            if (bike != null) {
                orderMap.put(orderHistoryList.get(i), bike);
                continue;
            }

            bike = normalCoupleBikeService.getByCodeBike(orderHistoryList.get(i).getBikeCode());
            if (bike != null) {
                orderMap.put(orderHistoryList.get(i), bike);
                continue;
            }

            bike = electricSingleBikeService.getByCodeBike(orderHistoryList.get(i).getBikeCode());
            if (bike != null) {
                orderMap.put(orderHistoryList.get(i), bike);
            }
        }
        return orderMap;
    }

    @Override
    public OrderHistory getOrderById(Long id) {
        return orderRepository.getById(id);
    }
}
