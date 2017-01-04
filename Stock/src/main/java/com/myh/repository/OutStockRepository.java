package com.myh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myh.domain.OutStock;

@Repository
public interface OutStockRepository extends JpaRepository<OutStock, String>, JpaSpecificationExecutor<OutStock>{

}
