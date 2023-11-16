package beanComp.models;

import beanComp.exeption.NoData;
import beanComp.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@SessionScope
@Component
@AllArgsConstructor
public class CartOrders {
    private ProductRepository repo;
    private final HashMap<Integer, Order> mapCartOrders;

    public int amountFromCartOrders(int id) {
        return mapCartOrders.containsKey(id) ? mapCartOrders.get(id).getAmount() : 0;
    }
    public void addOrder(Order order) {
        Integer id = order.getProductId();

        mapCartOrders.compute(id,
                (k, v) -> {
                    var product = repo.findById(id)
                            .orElseThrow(() -> {throw new NoData("Продукт не найден");});

                    var newObjOrder = v == null
                            ? new Order(order.getProductId(), 0, product.getDescr())
                            : v;
                    var newAmountForOrder = newObjOrder.getAmount() + order.getAmount();
                    var newAmountForRepo = product.getCurrBalance() - order.getAmount();

                    newObjOrder.setAmount(newAmountForOrder);
                    product.setCurrBalance(newAmountForRepo);

                    repo.save(product);

                    return newObjOrder;
                });
    }

}
