package com.myh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myh.domain.OutStockItem;

public interface OutStockItemRepository extends JpaRepository<OutStockItem, Integer>, JpaSpecificationExecutor<OutStockItem>{

}
