package com.yaf.florabasket.repository;

import com.yaf.florabasket.model.Flower;
import com.yaf.florabasket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ardafakili
 * @date 17.05.2020
 */
@Repository("flowerRepository")
public interface FlowerRepository extends JpaRepository<Flower, Long> {

    List<Flower> findBy();

    List<Flower> findAllBySellers(User seller);

    List<Flower> findAll();

    List<Flower> findAllByColor(String color);

    List<Flower> findAllByCategory(String category);

    List<Flower> findAllByName(String name);

}
