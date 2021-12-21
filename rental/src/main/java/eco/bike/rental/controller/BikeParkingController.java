package eco.bike.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BikeParkingController {
    @GetMapping("/")
    public String getHome(){
        return "redirect:/bike-parking-list";
    }

    @GetMapping("/bike-parking-list")
    public String getBikeParkingList(){
        return "bikeParkingList";
    }

    @GetMapping("/bike-parking-list/{id}")
    public String getBikeParkingDetails(@PathVariable ("id") Long id, Model model){
        //handle
        System.out.println(id);
        return "bikeParkingDetails";
    }
}
