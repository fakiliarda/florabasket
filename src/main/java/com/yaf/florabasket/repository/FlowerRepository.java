package com.yaf.florabasket.repository;

import com.yaf.florabasket.model.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author ardafakili
 * @date 17.05.2020
 */
@Repository("flowerRepository")
public interface FlowerRepository extends JpaRepository<Flower, Long> {

    List<Flower> findBy();

    Optional<Flower> findById(Long id);

}
