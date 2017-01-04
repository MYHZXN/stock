package com.myh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myh.domain.Good;

@Repository
public interface GoodRepository extends JpaRepository<Good, String>,JpaSpecificationExecutor<Good>{
}
