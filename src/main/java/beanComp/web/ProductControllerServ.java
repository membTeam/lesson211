package beanComp.web;

import beanComp.models.Order;

import java.util.List;

public interface ProductControllerServ {

    String AddOrder(List<Order> lsOrder);

    List<Order> GetOrder();
}
