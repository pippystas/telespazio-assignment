package com.telespazio.pantry.backend.dto;

public record InventoryItemResponse(Long id, String name, int quantity, int minThreshold, boolean isLowStock) {
}