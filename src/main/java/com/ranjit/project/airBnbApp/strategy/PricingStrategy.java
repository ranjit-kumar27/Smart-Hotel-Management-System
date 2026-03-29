package com.ranjit.project.airBnbApp.strategy;
import com.ranjit.project.airBnbApp.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface PricingStrategy {
    BigDecimal calculatePrice(Inventory inventory);

}
