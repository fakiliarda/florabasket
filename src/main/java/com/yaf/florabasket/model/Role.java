package com.yaf.florabasket.model;

import lombok.Data;

import javax.persistence.*;

/**
 *
 * @date 15.05.2020
 */

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "role")
    private String role;

}
