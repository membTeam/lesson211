package beanComp.models;

import beanComp.exeption.NoData;
import beanComp.repository.ProductRepository;
import beanComp.web.ProductControllerServ;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductControllerServImpl implements ProductControllerServ {
    private ProductRepository repo;
    private HashMap<Integer, List<Order>> mapOrder = new HashMap<>();

    public ProductControllerServImpl(ProductRepository repo) {
        this.repo = repo;
    }

    private void verifyBalance(List<Order> lsOrder) {

        lsOrder.stream().forEach(order -> {

            var product = repo.findById(order.getProductId())
                    .orElseThrow(() -> {
                        throw new NoData("Нет данных по продукту");
                    });

            var countFromRepo = product.getCurrBalance();

            var countFromMapOrder = allAmountFromMapOrder(product.getId());
            var balance = countFromRepo - countFromMapOrder - order.getAmount();

            if (balance < 0) {
                var endProduct = countFromRepo - countFromMapOrder;
                var mes = endProduct == 0
                        ? " нет в наличии"
                        : " в наличии только " + endProduct + "шт.";

                throw new NoData(product.getDescr() + mes);
            }

        });
    }

    private int allAmountFromMapOrder(Integer productId) {
        var anonimusObj = new Object() {
            int countFromMapOrder = 0;
        };

        mapOrder.entrySet().stream().forEach((data) -> {
            anonimusObj.countFromMapOrder += data.getValue()
                    .stream()
                    .filter(order -> order.getProductId() == productId)
                    .map(order -> order.getAmount())
                    .mapToInt(Integer::intValue)
                    .sum();
        });

        return anonimusObj.countFromMapOrder;
    }

    @Override
    public String AddOrder(OrderRequest orderRequest) {

        final Integer ORDERKEY = orderRequest.getKey();

        if (!mapOrder.containsKey(ORDERKEY)) {
            throw new NoData("KEY заказа не найден");
        }

        final var lsOrderFromMapOrder = mapOrder.get(ORDERKEY);
        final var lsOrderFromRequest = orderRequest.getListOrder()
                .stream()
                .map(order -> {
                    var product = repo.findById(order.getProductId())
                            .orElseThrow(() -> {
                                throw new NoData("Нет данных по продукту");
                            });

                    order.setDescr(product.getDescr());

                    return order;
                })
                .collect(Collectors.toList());

        verifyBalance(orderRequest.getListOrder());

        lsOrderFromMapOrder.addAll(lsOrderFromRequest);

        mapOrder.replace(ORDERKEY, lsOrderFromMapOrder);

        return "order added";
    }

    @Override
    public Integer getKeyAuthorisation() {
        int min = 100;
        int max = 200;
        int diff = max - min;

        Random random = new Random();
        var key = random.nextInt(diff + 1) + min;

        mapOrder.put(key, new ArrayList<>());

        return key;
    }

    @Override
    public List<Order> GetOrder(Integer key) {
        if (key == null || !mapOrder.containsKey(key)) {
            throw new NoData("Нет данных");
        }

        return mapOrder.get(key);
    }
}
