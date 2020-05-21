package com.yaf.florabasket.service.iml;

import com.yaf.florabasket.model.Cart;
import com.yaf.florabasket.model.Flower;
import com.yaf.florabasket.model.Role;
import com.yaf.florabasket.model.User;
import com.yaf.florabasket.repository.CartRepository;
import com.yaf.florabasket.repository.RoleRepository;
import com.yaf.florabasket.repository.UserRepository;
import com.yaf.florabasket.security.Roles;
import com.yaf.florabasket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @date 15.05.2020
 */

@Service("userService")
public class UserServiceIml implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceIml(@Qualifier("userRepository") UserRepository userRepository, @Qualifier("roleRepository") RoleRepository roleRepository, @Qualifier("cartRepository") CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveClientUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role userRole = roleRepository.findByRole(Roles.CLIENT.toString());
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

        userRepository.save(user);

        Cart cart = new Cart();
        cart.setClient(user);
        cartRepository.save(cart);
    }

    @Override
    public void saveSellerUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role userRole = roleRepository.findByRole(Roles.SELLER.toString());
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void saveCourierUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role userRole = roleRepository.findByRole(Roles.COURIER.toString());
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(userRepository.getOne(id));
    }

    @Override
    public List<User> listAllSellersByFlower(Flower flower) {
        return userRepository.findAllByFlowers(flower);
    }

    @Override
    public void createProduct(Flower flower) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User seller = findByEmail(auth.getName());
        seller.setFlowers(new HashSet<Flower>(Arrays.asList(flower)));
    }

    @Override
    public List<User> listAllCouriers() {
        return userRepository.findAllByRoles(roleRepository.findByRole(Roles.COURIER.toString()));
    }

}
