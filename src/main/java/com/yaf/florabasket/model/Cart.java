package com.yaf.florabasket.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author ardafakili
 * @date 17.05.2020
 */

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    @Getter
    @Setter
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @Getter
    @Setter
    private User client;

    @OneToMany(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Set<Orders> orders;

}
