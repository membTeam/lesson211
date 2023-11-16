package beanComp.models;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private int productId;
    private int amount;
    private String descr;
}
