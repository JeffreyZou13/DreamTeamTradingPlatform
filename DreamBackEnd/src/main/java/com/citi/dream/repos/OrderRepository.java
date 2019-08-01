package com.citi.dream.repos;

import com.citi.dream.jms.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByStrategyID(String id);
    List<Order> findByStrategyTypeOrderByWhenAsDate(String type);
    List<Order> findByStrategyIDOrderByWhenAsDate(String id);
}
