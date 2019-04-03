package com.epam.orderprocessing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {

    private final OrderRepository orderRepository;

    public HomeController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        orderRepository.save(new Order("2747 Cambridge Court, Highlands, NC 28741", "Scott M Faris", "Full Report", 500, 14));
        orderRepository.save(new Order("3188 Friendship Lane, Santa Clara, CA 95054", "Sherry D Presnell", "Limited Plus Report", 300, 9));
        orderRepository.save(new Order("680 Coburn Hollow Road, Peoria, IL 61614", "Debra C Clary", "Limited Plus Report", 250, 9));
        orderRepository.save(new Order("3630 Yorkshire Circle, Kitty Hawk, NC 27949", "Timothy J Silva", "Limited Report", 250, 5));
        orderRepository.save(new Order("2834 Dawson Drive, Jacksonville, AR 72099", "Elizabeth T McNulty", "Limited Report", 150, 3));

        model.put("orders", orderRepository.findAll());

        return "setup";
    }
}