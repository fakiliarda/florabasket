package com.yaf.florabasket.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * @author ardafakili
 * @date 17.05.2020
 */

@Entity
@Table(name="flower")
@ToString
public class Flower {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    @Getter
    @Setter
    private Long id;

    @NonNull
    @Column(name="name")
    @Getter
    @Setter
    private String name;

    @NonNull
    @Column(name="price")
    @Getter
    @Setter
    private String price;

    @NonNull
    @Column(name="imagelink")
    @Getter
    @Setter
    private String imagelink;

    @NonNull
    @Column(name="hoverImageLink")
    @Getter
    @Setter
    private String hoverImageLink;

    @NonNull
    @Column(name="imagelink2")
    @Getter
    @Setter
    private String imagelink2;

    @NonNull
    @Column(name="imagelink3")
    @Getter
    @Setter
    private String imagelink3;

    @NonNull
    @Column(name="imagelink4")
    @Getter
    @Setter
    private String imagelink4;

    @NonNull
    @Column(name="explanation", length = 100000 )
    @Getter
    @Setter
    private String explanation;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "flower_seller", joinColumns = @JoinColumn(name = "seller_id"), inverseJoinColumns = @JoinColumn(name = "flower_id"))
    @Getter
    @Setter
    private Set<User> sellers;

}
