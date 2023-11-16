package beanComp.web;

import beanComp.exeption.NoData;
import beanComp.models.CartOrders;
import beanComp.models.Order;
import beanComp.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductControllerServImpl implements ProductControllerServ {

    private ProductRepository repo;
    private CartOrders cartOrders;

    private void verifyBalance(List<Order> lsOrder) {

        lsOrder.stream().forEach(order -> {

            if (order.getAmount() == null || order.getAmount() == 0) {
                throw new NoData("Нет данных по количеству продукции");
            }

            var product = repo.findById(order.getProductId())
                    .orElseThrow(() -> {
                        throw new NoData("Нет данных по продукту");
                    });

            var countFromRepo = product.getCurrBalance();
            var balance = countFromRepo - order.getAmount();

            if (balance < 0) {

                if (countFromRepo == 0) {
                    balance = 0;
                }

                var mes = balance == 0
                        ? " нет в наличии"
                        : " в наличии только " + countFromRepo + " шт.";

                throw new NoData(product.getDescr() + mes);
            }

        });
    }

    @Override
    public String AddOrder(List<Order> lsOrderRequest) {

        verifyBalance(lsOrderRequest);

        lsOrderRequest
                .stream()
                .forEach(order -> {
                    var product = repo.findById(order.getProductId())
                            .orElseThrow(() -> {
                                throw new NoData("Нет данных по продукту");
                            });

                    order.setDescr(product.getDescr());
                    cartOrders.addOrder(order);
                });

        return "order added";
    }

    @Override
    public List<Order> GetOrder() {
        return cartOrders.getMapCartOrders()
                .values()
                .stream()
                .collect(Collectors.toList());
    }
}
