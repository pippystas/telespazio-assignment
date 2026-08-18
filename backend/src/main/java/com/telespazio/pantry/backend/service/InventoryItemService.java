package com.telespazio.pantry.backend.service;

import org.springframework.stereotype.Service;

import com.telespazio.pantry.backend.dto.InventoryItemResponse;
import com.telespazio.pantry.backend.entity.InventoryItem;
import com.telespazio.pantry.backend.repository.InventoryItemRepository;

import java.util.List;
import java.util.ArrayList;

@Service
public class InventoryItemService {

    private final InventoryItemRepository repository;

    public InventoryItemService(InventoryItemRepository repository) {
        this.repository = repository;
    }

    public List<InventoryItemResponse> getAllItems() {
        List<InventoryItem> items = repository.findAll();
        List<InventoryItemResponse> responses = new ArrayList<>();

        for (InventoryItem item : items) {
            boolean isLowStock = item.isLowStock();
            InventoryItemResponse response = new InventoryItemResponse(
                    item.getId(),
                    item.getName(),
                    item.getQuantity(),
                    item.getMinThreshold(),
                    isLowStock);
            responses.add(response);
        }
        return responses;
    }

    public InventoryItemResponse createItem(InventoryItem item) {
        InventoryItem savedItem = repository.save(item);
        boolean isLowStock = savedItem.isLowStock();
        return new InventoryItemResponse(
                savedItem.getId(),
                savedItem.getName(),
                savedItem.getQuantity(),
                savedItem.getMinThreshold(),
                isLowStock);
    }

    public InventoryItemResponse restockItem(Long id, int amount) {
        InventoryItem item = repository.findById(id).orElseThrow(() -> new RuntimeException("Item not found: " + id));

        item.setQuantity(item.getQuantity() + amount);
        repository.save(item);

        boolean isLowStock = item.isLowStock();
        return new InventoryItemResponse(item.getId(), item.getName(), item.getQuantity(), item.getMinThreshold(),
                isLowStock);

    }

    public void deleteItem(Long id) {
        repository.deleteById(id);
    }
}