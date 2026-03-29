package com.ranjit.project.airBnbApp.strategy;

import com.ranjit.project.airBnbApp.entity.Inventory;

import java.math.BigDecimal;

public class BasePricingStrategy implements PricingStrategy {
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
