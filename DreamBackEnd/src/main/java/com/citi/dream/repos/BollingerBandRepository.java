package com.citi.dream.repos;

import com.citi.dream.strategies.BollingerBand;
import com.citi.dream.strategies.TwoMovingAverages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BollingerBandRepository extends JpaRepository<BollingerBand, String> {

}
