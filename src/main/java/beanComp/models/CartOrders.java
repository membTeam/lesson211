package beanComp.models;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.List;

@Data
@SessionScope
@Component
public class CartOrders {

    private final HashMap<Integer, Order> mapCartOrders = new HashMap<>();

    public int amountFromCartOrders(int id) {
        return mapCartOrders.containsKey(id) ? mapCartOrders.get(id).getAmount() : 0;
    }
    public void addOrder(Order order) {
        mapCartOrders.compute(order.getProductId(),
                (k, v) -> {
                    var newValue = v == null
                            ? new Order(order.getProductId(), 0, order.getDescr())
                            : v;

                    newValue.setAmount(newValue.getAmount() + order.getAmount());
                    return newValue;
                });

    }

}
