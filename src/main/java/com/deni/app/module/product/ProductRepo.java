package com.deni.app.module.product;


import com.deni.app.module.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    User findByName(String name);
}
