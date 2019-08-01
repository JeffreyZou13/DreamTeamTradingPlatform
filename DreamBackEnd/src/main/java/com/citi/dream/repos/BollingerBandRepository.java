package com.citi.dream.repos;

import com.citi.dream.strategies.BollingerBand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BollingerBandRepository extends JpaRepository<BollingerBand, String> {
    @Query(value="select b.strategyID, b.durationTime, b.numOfStddev, b.stockName, b.volume, b.cutOffPercentage, b.state, b.profit from BollingerBand b where state <> 'stopped'")
    List<BollingerBand> findAllNotStopped();
}
