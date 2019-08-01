package com.citi.dream.repos;

import com.citi.dream.strategies.TwoMovingAverages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TwoMovingAveragesRepository extends JpaRepository<TwoMovingAverages, String> {

    @Query(value="select t.strategyID, t.longTime, t.shortTime, t.stockName, t.volume, t.cutOffPercentage, t.state, t.profit from TwoMovingAverages t where state <> 'stopped'")
    List<TwoMovingAverages> findAllNotStopped();
}
