package beanComp.models;

import beanComp.exeption.NoData;
import beanComp.repository.ProductRepository;
import beanComp.web.ProductControllerServ;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class ProductControllerServImpl implements ProductControllerServ {
    private ProductRepository repo;
    private HashMap<Integer, List<Order>> mapOrder = new HashMap<>();
    public ProductControllerServImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public String AddOrder(OrderRequest orderRequest) {

        Integer key = orderRequest.getKey();

        if (!mapOrder.containsKey(key)) {
            throw new NoData("key не найден");
        }

        var lsOrderOld = mapOrder.get(key);
        var newLs = orderRequest.getListOrder()
                .stream()
                .map((val) -> {
                    var product = repo.findById(val.getProductId())
                            .orElseThrow(()->{ throw new NoData("Нет данных по продукту");
                    });

                    val.setDescr(product.getDescr());

                    return val;
                })
                .collect(Collectors.toList());

        lsOrderOld.addAll(newLs);

        mapOrder.replace(key, lsOrderOld);

        return "Заказ добавлен";
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
