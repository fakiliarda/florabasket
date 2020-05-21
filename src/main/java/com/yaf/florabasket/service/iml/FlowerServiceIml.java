package com.yaf.florabasket.service.iml;

import com.yaf.florabasket.model.Flower;
import com.yaf.florabasket.model.User;
import com.yaf.florabasket.repository.FlowerRepository;
import com.yaf.florabasket.repository.UserRepository;
import com.yaf.florabasket.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @date 17.05.2020
 */

@Service
public class FlowerServiceIml implements FlowerService {


    private final FlowerRepository flowerRepository;
    private final UserRepository userRepository;

    @Autowired
    public FlowerServiceIml(@Qualifier("flowerRepository") FlowerRepository flowerRepository, @Qualifier("userRepository") UserRepository userRepository) {
        this.flowerRepository = flowerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Flower> listAll() {
        return flowerRepository.findAll();
    }

    @Override
    public List<Flower> listAllBySeller(User seller) {
        return flowerRepository.findAllBySellers(seller);
    }

    @Override
    public Flower findById(Long id) {
        return flowerRepository.findById(id).get();
    }

    @Override
    public List<Flower> findAllByColor(String color) {
        return flowerRepository.findAllByColor(color);
    }

    @Override
    public List<Flower> findAllByCategory(String category) {
        return flowerRepository.findAllByCategory(category);
    }

    @Override
    public void addNewSeller(Flower flower) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());

        Flower flowerToUpdate = findById(flower.getId());
        flowerToUpdate.getSellers().add(user);

        flowerRepository.save(flowerToUpdate);

    }

    @Override
    public List<Flower> findAllByName(String value) {
        return flowerRepository.findAllByName(value);
    }
}
