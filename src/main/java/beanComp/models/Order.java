package beanComp.models;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private int productId;
    private String descr;
    private int amount;
}
