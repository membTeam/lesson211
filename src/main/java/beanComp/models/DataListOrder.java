package beanComp.models;

import java.util.ArrayList;
import java.util.List;

public class DataListOrder {
    private final List<Order> listDataOrder;

    public DataListOrder() {
        listDataOrder = new ArrayList<>();
    }

    public List<Order> getListDataOrder() {
        return listDataOrder;
    }

}
