package eco.bike.rental.controller;

import eco.bike.rental.dto.OrderInfoDTO;
import eco.bike.rental.entity.Card;
import eco.bike.rental.entity.OrderHistory;
import eco.bike.rental.entity.bike.ElectricSingleBike;
import eco.bike.rental.entity.bike.NormalCoupleBike;
import eco.bike.rental.entity.bike.NormalSingleBike;
import eco.bike.rental.service.IBikeService;
import eco.bike.rental.service.ICardService;
import eco.bike.rental.service.IOrderService;
import eco.bike.rental.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class OrderController {
    @Autowired
    private ICardService cardService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IBikeService<NormalSingleBike> normalSingleBikeService;

    @Autowired
    private IBikeService<NormalCoupleBike> normalCoupleBikeService;

    @Autowired
    private IBikeService<ElectricSingleBike> electricSingleBikeService;

    @GetMapping("/bike-renting/pay")
    private String getResultTransaction(
            @RequestParam(value = "success", required = false) String success,
            @RequestParam(value = "fulledForm", required = false) String fulledForm,
            @RequestParam(value = "availableCard", required = false) String availableCard,
            Model model
    ) {
        if (success != null)
            model.addAttribute("success", success);
        if (fulledForm != null)
            model.addAttribute("fulledForm", fulledForm);
        if (availableCard != null)
            model.addAttribute("availableCard", availableCard);
        return "payResult";
    }

    @PostMapping("/bike-renting/pay")
    public String executeTransaction(OrderInfoDTO orderInfoDTO) {
        if (orderInfoDTO.getOwner().isEmpty()
                || orderInfoDTO.getCardNumber().isEmpty()
                || orderInfoDTO.getIssuingBank().isEmpty()
                || orderInfoDTO.getExpirationDate().isEmpty()
                || orderInfoDTO.getCvvCode().isEmpty()
                || orderInfoDTO.getTransactionDescription().isEmpty()) {
            return "redirect:/bike-renting/pay?fulledForm=false";
        }

        Card card = new Card();
        if (cardService.getByCardNumber(orderInfoDTO.getCardNumber()) == null) { // never used before
            BeanUtils.copyProperties(orderInfoDTO, card);
            card = cardService.save(card);
        } else {
            card = cardService.getByCardNumber(orderInfoDTO.getCardNumber()); //used before
            if (!card.getIsAvailable()) {
                return "redirect:/bike-renting/pay?availableCard=false";
            }
        }

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setCard(card);

        // default user in system
        orderHistory.setUser(userService.getById(1L));

        orderHistory.setIsDone(false);

        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        orderHistory.setStartedAt(simpleDateFormat.format(new Date()));

        orderHistory.setBikeCode(orderInfoDTO.getRentingBikeCode());
        orderHistory.setBikeParkingStartId(orderInfoDTO.getRentingBikeParkingId());

        // this is set after receive response from api
        orderHistory.setIsSuccess(true);

        // set card isAvailable -> false
        card.setIsAvailable(false);
        cardService.save(card);

        // set bike is in using -> true
        NormalSingleBike bike = normalSingleBikeService.getByCodeBike(orderInfoDTO.getRentingBikeCode());
        if (bike != null) {
            bike.setBikeParking(null);
            bike.setUser(userService.getById(1L));
            normalSingleBikeService.save(bike);
        }

        NormalCoupleBike cbike = normalCoupleBikeService.getByCodeBike(orderInfoDTO.getRentingBikeCode());
        if (cbike != null) {
            cbike.setBikeParking(null);
            cbike.setUser(userService.getById(1L));
            normalCoupleBikeService.save(cbike);
        }

        ElectricSingleBike ebike = electricSingleBikeService.getByCodeBike(orderInfoDTO.getRentingBikeCode());
        if (ebike != null) {
            ebike.setBikeParking(null);
            ebike.setUser(userService.getById(1L));
            electricSingleBikeService.save(ebike);
        }

        orderService.save(orderHistory);

        return "redirect:/bike-renting/pay?success=true";
    }
}
