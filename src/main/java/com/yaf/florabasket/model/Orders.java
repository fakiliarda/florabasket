package com.yaf.florabasket.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

/**
 *
 * @date 11.05.2020
 */

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    @Getter
    @Setter
    private Long id;

    @NonNull
    @Column(name="quantity")
    @Getter
    @Setter
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flower_id", referencedColumnName = "id")
    @Getter
    @Setter
    private Flower flower;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @Getter
    @Setter
    private Cart cart;

    @ManyToOne/*(cascade = CascadeType.ALL)*/
    @JoinColumn(name = "courier_id", referencedColumnName = "id")
    @Getter
    @Setter
    private User courier;

    @ManyToOne/*(cascade = CascadeType.ALL)*/
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @Getter
    @Setter
    private User client;

    @ManyToOne/*(cascade = CascadeType.ALL)*/
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    @Getter
    @Setter
    private User seller;

    @Column(name = "status")
    @Getter
    @Setter
    private String status;

    @Column(name = "address")
    @Getter
    @Setter
    private String address;

    @Column(name = "note")
    @Getter
    @Setter
    private String note;

    @Column(name = "occasion")
    @Getter
    @Setter
    private String occasion;

    @Column(name = "deliveryType")
    @Getter
    @Setter
    private String deliveryType;

    @Column(name = "paymentMethod")
    @Getter
    @Setter
    private String paymentMethod;

    @Column(name = "paymentCompleted")
    @Getter
    @Setter
    private Boolean paymentCompleted;

    @Column(name = "deliveryStatus")
    @Getter
    @Setter
    private String deliveryStatus;

}
