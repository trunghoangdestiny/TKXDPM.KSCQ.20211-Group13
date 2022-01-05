package eco.bike.rental.controller;

import eco.bike.rental.entity.BikeParking;
import eco.bike.rental.entity.OrderHistory;
import eco.bike.rental.entity.bike.BaseBike;
import eco.bike.rental.entity.bike.ElectricSingleBike;
import eco.bike.rental.entity.bike.NormalCoupleBike;
import eco.bike.rental.entity.bike.NormalSingleBike;
import eco.bike.rental.service.IBikeParkingService;
import eco.bike.rental.service.IBikeService;
import eco.bike.rental.service.IOrderService;
import eco.bike.rental.utils.ICalculateFee;
import eco.bike.rental.utils.impl.NormalCalculateFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class BikeBackingController {
    @Autowired
    private IBikeParkingService bikeParkingService;

    @Autowired
    private IBikeService<NormalSingleBike> normalSingleBikeService;

    @Autowired
    private IBikeService<NormalCoupleBike> normalCoupleBikeIBikeService;

    @Autowired
    private IBikeService<ElectricSingleBike> electricSingleBikeIBikeService;

    @Autowired
    private IOrderService orderService;

    @GetMapping("/bike-backing")
    public String getBikeParkingList(
            @RequestParam("bikeCode") String codeBike,
            @RequestParam("orderId") Long orderId,
            Model model
    ){
        List<BikeParking> bikeParkingList = bikeParkingService.findAll();
        model.addAttribute("bikeParkingList", bikeParkingList);

        model.addAttribute("bikeCode", codeBike);
        model.addAttribute("orderId", orderId);
        return "chooseBikeParking";
    }

    @GetMapping("/bike-backing/pay")
    public ModelAndView getBackingPaymentInfo(
        @RequestParam("bikeCode") String codeBike,
        @RequestParam("orderId") Long orderId,
        @RequestParam("bikeParkingId") Long bikeParkingId,
        ModelMap model
    ){
        BikeParking bikeParking = bikeParkingService.getById(bikeParkingId);
        model.addAttribute("bikeParking", bikeParking);

        OrderHistory orderHistory = orderService.getOrderById(orderId);


        String pattern = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        long usedTime = 0;
        try {
            Date startTime = simpleDateFormat.parse(orderHistory.getStartedAt().split(" ")[1]);
            String currentTimeString = simpleDateFormat.format(new Date());
            Date currentTime = simpleDateFormat.parse(currentTimeString);
            long diff = currentTime.getTime() - startTime.getTime();
            TimeUnit timeUnit = TimeUnit.SECONDS;
            usedTime = timeUnit.convert(diff, TimeUnit.MILLISECONDS); // time in seconds
            String currentRentedTime = usedTime / 3600 + "h " + (usedTime % 3600) / 60 + "m " + (usedTime % 60) + "s";
            orderHistory.setCurrentRentedTime(currentRentedTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //calculate fee
        ICalculateFee normalCalculateFee = new NormalCalculateFee();
        orderHistory.setCurrentPrice(normalCalculateFee.calculateFee(usedTime));
        //save to db
        orderHistory = orderService.save(orderHistory);


        model.addAttribute("order", orderHistory);

        BaseBike bike = normalSingleBikeService.getByCodeBike(codeBike);
        if (bike != null){
            model.addAttribute("bike", bike);
            return new ModelAndView("bikeBackingPayment", model);
        }
        bike = normalCoupleBikeIBikeService.getByCodeBike(codeBike);
        if (bike != null){
            model.addAttribute("bike", bike);
            return new ModelAndView("bikeBackingPayment", model);
        }
        bike = electricSingleBikeIBikeService.getByCodeBike(codeBike);
        if (bike != null){
            model.addAttribute("bike", bike);
            return new ModelAndView("bikeBackingPayment", model);
        }

        model.addAttribute("getBackingInfo", false);
        return new ModelAndView("redirect:/rental-bike-list", model);
    }
}
