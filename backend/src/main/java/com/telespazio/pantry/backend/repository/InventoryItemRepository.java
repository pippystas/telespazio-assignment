package com.telespazio.pantry.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telespazio.pantry.backend.entity.InventoryItem;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
}