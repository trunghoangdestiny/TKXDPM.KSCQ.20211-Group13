package eco.bike.rental.controller;

import eco.bike.rental.dto.BackingInfoDTO;
import eco.bike.rental.dto.OrderInfoDTO;
import eco.bike.rental.entity.Card;
import eco.bike.rental.entity.OrderHistory;
import eco.bike.rental.entity.bike.ElectricSingleBike;
import eco.bike.rental.entity.bike.NormalCoupleBike;
import eco.bike.rental.entity.bike.NormalSingleBike;
import eco.bike.rental.interbank.PayOrder;
import eco.bike.rental.service.*;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
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

    @Autowired
    private IBikeParkingService bikeParkingService;

    @Autowired
    private PayOrder payOrder;

    @GetMapping("/bike-renting/pay")
    private String getResultTransaction(
            @RequestParam(value = "success", required = false) String success,
            @RequestParam(value = "fulledForm", required = false) String fulledForm,
            @RequestParam(value = "availableCard", required = false) String availableCard,

            @RequestParam(value = "invalidCard", required = false) String invalidCard,
            @RequestParam(value = "notEnoughBalance", required = false) String notEnoughBalance,
            @RequestParam(value = "serverError", required = false) String serverError,
            @RequestParam(value = "cheatTransaction", required = false) String cheatTransaction,
            @RequestParam(value = "notEnoughInfo", required = false) String notEnoughInfo,
            @RequestParam(value = "versionInfo", required = false) String versionInfo,
            @RequestParam(value = "invalidAmount", required = false) String invalidAmount,
            Model model
    ) {
        model.addAttribute("success", success);
        model.addAttribute("fulledForm", fulledForm);
        model.addAttribute("availableCard", availableCard);

        model.addAttribute("invalidCard", invalidCard);
        model.addAttribute("notEnoughBalance", notEnoughBalance);
        model.addAttribute("serverError", serverError);
        model.addAttribute("cheatTransaction", cheatTransaction);
        model.addAttribute("notEnoughInfo", notEnoughInfo);
        model.addAttribute("versionInfo", versionInfo);
        model.addAttribute("invalidAmount", invalidAmount);
        return "payResult";
    }

    @PostMapping("/bike-renting/pay")
    public String executeRentalTransaction(OrderInfoDTO orderInfoDTO) throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException {
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

        // call api
        JSONObject responseBody = payOrder.payOrder(
                card,
                orderInfoDTO.getAmount(),
                orderInfoDTO.getTransactionDescription(),
                "pay"
        );
//        System.out.println(responseBody.getString("errorCode"));

        switch (responseBody.getString("errorCode")) {
            case "00":
                orderHistory.setIsSuccess(true);
                break;
            case "01":
                return "redirect:/bike-renting/pay?invalidCard=true";
            case "02":
                return "redirect:/bike-renting/pay?notEnoughBalance=true";
            case "03":
                return "redirect:/bike-renting/pay?serverError=true";
            case "04":
                return "redirect:/bike-renting/pay?cheatTransaction=true";
            case "05":
                return "redirect:/bike-renting/pay?notEnoughInfo=true";
            case "06":
                return "redirect:/bike-renting/pay?versionInfo=false";
            case "07":
                return "redirect:/bike-renting/pay?invalidAmount=true";
        }

//        orderHistory.setIsSuccess(true);

        // set card isAvailable -> false
        card.setIsAvailable(false);
        cardService.save(card);

        // set bike
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

        orderHistory.setDeposit(orderInfoDTO.getAmount());
        orderService.save(orderHistory);

        return "redirect:/bike-renting/pay?success=true";
    }

    @GetMapping("/bike-backing/result")
    private String getResultBacking(
            @RequestParam(value = "success", required = false) String success,
            @RequestParam(value = "fulledForm", required = false) String fulledForm,
            @RequestParam(value = "availableCard", required = false) String availableCard,
            @RequestParam(value = "notExistCard", required = false) String notExistCard,

            @RequestParam(value = "invalidCard", required = false) String invalidCard,
            @RequestParam(value = "notEnoughBalance", required = false) String notEnoughBalance,
            @RequestParam(value = "serverError", required = false) String serverError,
            @RequestParam(value = "cheatTransaction", required = false) String cheatTransaction,
            @RequestParam(value = "notEnoughInfo", required = false) String notEnoughInfo,
            @RequestParam(value = "versionInfo", required = false) String versionInfo,
            @RequestParam(value = "invalidAmount", required = false) String invalidAmount,
            Model model
    ) {
        model.addAttribute("success", success);
        model.addAttribute("fulledForm", fulledForm);
        model.addAttribute("availableCard", availableCard);
        model.addAttribute("notExistCard", notExistCard);

        model.addAttribute("invalidCard", invalidCard);
        model.addAttribute("notEnoughBalance", notEnoughBalance);
        model.addAttribute("serverError", serverError);
        model.addAttribute("cheatTransaction", cheatTransaction);
        model.addAttribute("notEnoughInfo", notEnoughInfo);
        model.addAttribute("versionInfo", versionInfo);
        model.addAttribute("invalidAmount", invalidAmount);
        return "payResult";
    }

    @PostMapping("/bike-backing/pay")
    public String executeBackingTransaction(BackingInfoDTO backingInfoDTO) throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException {
        if (backingInfoDTO.getOwner().isEmpty()
                || backingInfoDTO.getCardNumber().isEmpty()
                || backingInfoDTO.getIssuingBank().isEmpty()
                || backingInfoDTO.getExpirationDate().isEmpty()
                || backingInfoDTO.getCvvCode().isEmpty()
                || backingInfoDTO.getTransactionDescription().isEmpty()) {
            return "redirect:/bike-backing/result?fulledForm=false";
        }

        Card card = cardService.getByCardNumber(backingInfoDTO.getCardNumber());
        if (card == null) {
            return "redirect:/bike-backing/result?notExistCard=true";
        }

        OrderHistory orderHistory = orderService.getOrderById(backingInfoDTO.getOrderId());

        // call api
        JSONObject responseBody = payOrder.payOrder(
                card,
                orderHistory.getDeposit() - backingInfoDTO.getAmount(),
                backingInfoDTO.getTransactionDescription(),
                "refund"
        );
//        System.out.println(responseBody.getString("errorCode"));

        switch (responseBody.getString("errorCode")) {
            case "00":
                orderHistory.setIsSuccess(true);
                break;
            case "01":
                return "redirect:/bike-renting/pay?invalidCard=true";
            case "02":
                return "redirect:/bike-renting/pay?notEnoughBalance=true";
            case "03":
                return "redirect:/bike-renting/pay?serverError=true";
            case "04":
                return "redirect:/bike-renting/pay?cheatTransaction=true";
            case "05":
                return "redirect:/bike-renting/pay?notEnoughInfo=true";
            case "06":
                return "redirect:/bike-renting/pay?versionInfo=false";
            case "07":
                return "redirect:/bike-renting/pay?invalidAmount=true";
        }

//        orderHistory.setIsSuccess(true);

        orderHistory.setIsDone(true);

        // after order, card is available -> true
        card.setIsAvailable(true);
        cardService.save(card);

        //set bike
        NormalSingleBike bike = normalSingleBikeService
                .getByCodeBike(backingInfoDTO.getCodeBike());
        if (bike != null) {
            bike.setBikeParking(bikeParkingService.getById(backingInfoDTO.getBikeParkingId()));
            bike.setUser(null);
            normalSingleBikeService.save(bike);
        }

        NormalCoupleBike cbike = normalCoupleBikeService
                .getByCodeBike(backingInfoDTO.getCodeBike());
        if (cbike != null) {
            cbike.setBikeParking(bikeParkingService.getById(backingInfoDTO.getBikeParkingId()));
            cbike.setUser(null);
            normalCoupleBikeService.save(cbike);
        }

        ElectricSingleBike ebike = electricSingleBikeService
                .getByCodeBike(backingInfoDTO.getCodeBike());
        if (ebike != null) {
            ebike.setBikeParking(bikeParkingService.getById(backingInfoDTO.getBikeParkingId()));
            ebike.setUser(null);
            electricSingleBikeService.save(ebike);
        }

        orderService.save(orderHistory);

        return "redirect:/bike-backing/result?success=true";
    }
}
