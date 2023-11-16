package beanComp.web;

import beanComp.models.CartOrders;
import beanComp.models.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import beanComp.exeption.NoData;
import beanComp.repository.ProductRepository;
import beanComp.web.ProductControllerServ;

@Component
@AllArgsConstructor
public class ProductControllerServImpl implements ProductControllerServ {

    private ProductRepository repo;
    private CartOrders cartOrders;

    private void verifyBalance(List<Order> lsOrder) {

        lsOrder.stream().forEach(order -> {

            var product = repo.findById(order.getProductId())
                    .orElseThrow(() -> {
                        throw new NoData("Нет данных по продукту");
                    });

            var countFromRepo = product.getCurrBalance();

            var countFromCartOrders = cartOrders.amountFromCartOrders(order.getProductId()) ;
            var balance = countFromRepo - countFromCartOrders - order.getAmount();

            if (balance < 0) {
                var endProduct = countFromRepo - countFromCartOrders;
                var mes = endProduct == 0
                        ? " нет в наличии"
                        : " в наличии только " + endProduct + "шт.";

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
