package com.hixtrip.sample.domain.inventory.repository;

/**
 *
 */
public interface InventoryRepository {

    /**
     * 扣减内存库存
     */
    void dedInventory(String tradeNo );

}
