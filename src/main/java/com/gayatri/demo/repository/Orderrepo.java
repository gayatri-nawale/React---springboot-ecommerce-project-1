package com.gayatri.demo.repository;

import com.gayatri.demo.models.Order;
import com.gayatri.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Orderrepo extends JpaRepository<Order,Integer> {
    Order findByOrderid(String orderid);
}
