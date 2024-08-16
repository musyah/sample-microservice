package com.service.inventory.controller;

import com.service.inventory.dto.InventoryResponse;
import com.service.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public List<InventoryResponse> checkinventory(@RequestParam List<String> skuCodes){
        return inventoryService.validate(skuCodes);

    }
}
