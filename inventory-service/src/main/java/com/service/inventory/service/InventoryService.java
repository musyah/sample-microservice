package com.service.inventory.service;

import com.service.inventory.dto.InventoryResponse;
import com.service.inventory.model.Inventory;
import com.service.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class InventoryService {

    private  final InventoryRepository inventoryRepository;

    @SneakyThrows
    public List<InventoryResponse> validate(List<String> skuCodes) {
        log.info("wait.started");
        List<InventoryResponse> result = inventoryRepository.findBySkuCodeIn(skuCodes).stream().map(this::mapToResponse).toList();

        Thread.sleep(10000);
        log.info("wait.finished");

       return result;
    }

    private InventoryResponse mapToResponse(Inventory inventory) {
        return InventoryResponse.fromInventory(inventory);
    }
}
