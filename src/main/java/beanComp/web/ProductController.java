package beanComp.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import beanComp.models.Order;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/store/order", produces="application/json")
public class ProductController {
    private ProductControllerServImpl servImpl;

    @PostMapping(path="/add", consumes="application/json")  // /store/order/add
    public String AddOrder(@RequestBody List<Order> listOrder ) {
        return servImpl.AddOrder(listOrder);
    }

    @GetMapping("/get/{key}")
    public List<Order> GetOrder() {
        return servImpl.GetOrder();
    }
}
