package com.telespazio.pantry.backend.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telespazio.pantry.backend.dto.InventoryItemResponse;
import com.telespazio.pantry.backend.entity.InventoryItem;
import com.telespazio.pantry.backend.service.InventoryItemService;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class InventoryItemController {
    private final InventoryItemService service;

    public InventoryItemController(InventoryItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<InventoryItemResponse> getAllItems() {
        return service.getAllItems();
    }

    @PostMapping
    public InventoryItemResponse createItem(@RequestBody InventoryItem item) {
        return service.createItem(item);
    }

    @PatchMapping("/{id}/restock")
    public InventoryItemResponse restockItem(@PathVariable Long id, @RequestBody RestockRequest request) {
        return service.restockItem(id, request.quantity());
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
    }
}
