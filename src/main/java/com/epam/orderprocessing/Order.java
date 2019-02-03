package com.epam.orderprocessing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "Orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String propertyName;
    private String borrowerName;
    private String productType;
    private int cost;
    private int slaDays;

    public Order(String propertyName, String borrowerName, String productType, int cost, int slaDays) {
        this.propertyName = propertyName;
        this.borrowerName = borrowerName;
        this.productType = productType;
        this.cost = cost;
        this.slaDays = slaDays;
    }
}