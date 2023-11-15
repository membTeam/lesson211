package beanComp.web;

import beanComp.models.Order;
import beanComp.models.OrderRequest;

import java.util.List;

public interface ProductControllerServ {

    String AddOrder(OrderRequest orderExt);

    Integer getKeyAuthorisation();

    List<Order> GetOrder(Integer key);
}
