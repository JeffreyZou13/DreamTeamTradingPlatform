package com.citi.dream.repos;

import com.citi.dream.strategies.TwoMovingAverages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoMovingAveragesRepository extends JpaRepository<TwoMovingAverages, String> {

}
