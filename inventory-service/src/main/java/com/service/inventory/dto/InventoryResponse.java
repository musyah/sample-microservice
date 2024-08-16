package com.service.inventory.dto;

import com.service.inventory.model.Inventory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryResponse {

    private String skuCode;
    private boolean isAvailable;

    public static InventoryResponse fromInventory(Inventory inventory) {
        return InventoryResponse.builder()
                .skuCode(inventory.getSkuCode())
                .isAvailable(inventory.getQuantity()>0)
                .build();
    }

}
