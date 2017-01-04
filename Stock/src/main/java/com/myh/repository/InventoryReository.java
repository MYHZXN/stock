package com.myh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myh.domain.Inventory;

@Repository
public interface InventoryReository extends JpaRepository<Inventory, String>, JpaSpecificationExecutor<Inventory>{

}
