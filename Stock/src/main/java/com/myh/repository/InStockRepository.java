package com.myh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myh.domain.InStock;

@Repository
public interface InStockRepository extends JpaRepository<InStock, String>, JpaSpecificationExecutor<InStock>{

}
