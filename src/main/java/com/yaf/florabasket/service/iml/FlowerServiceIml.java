package com.yaf.florabasket.service.iml;

import com.yaf.florabasket.model.Flower;
import com.yaf.florabasket.repository.FlowerRepository;
import com.yaf.florabasket.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ardafakili
 * @date 17.05.2020
 */

@Service
public class FlowerServiceIml implements FlowerService {


    private final FlowerRepository flowerRepository;

    @Autowired
    public FlowerServiceIml(@Qualifier("flowerRepository") FlowerRepository flowerRepository) {
        this.flowerRepository = flowerRepository;
    }

    @Override
    public List<Flower> listAll() {
        return flowerRepository.findBy();
    }

    @Override
    public Flower findById(Long id) {
        return flowerRepository.findById(id).get();
    }
}
