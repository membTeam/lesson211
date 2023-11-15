package beanComp.web;


import beanComp.models.Order;
import beanComp.models.ProductControllerServImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import beanComp.models.OrderRequest;

/*@Data
@AllArgsConstructor
class OrderExt{
    private int key;
    private List<Order> listOrder;
}*/

@RestController
@RequestMapping(path = "/store/order", produces="application/json")
public class ProductController {
    private ProductControllerServImpl servImpl;

    public ProductController(ProductControllerServImpl servImpl) {
        this.servImpl = servImpl;
    }

    @GetMapping("/key-authorisation")  // /store/order/key-authorisation
    public Integer getKeyAuthorisation() {
        return servImpl.getKeyAuthorisation();
    }

    @PostMapping(path="/add", consumes="application/json")  // /store/order/add
    public String AddOrder(@RequestBody OrderRequest orderExt ) {
        return servImpl.AddOrder(orderExt);
    }

    @GetMapping("/get/{key}")
    public List<Order> GetOrder(@PathVariable("key") Integer key ) {
        return servImpl.GetOrder(key);
    }
}
