package com.myh.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.myh.domain.InStockItem;

public interface InStockItemRepository 
	extends JpaRepository<InStockItem, Integer>, 
			JpaSpecificationExecutor<InStockItem>{

}
