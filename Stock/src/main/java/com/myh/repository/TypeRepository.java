package com.myh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myh.domain.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer>, JpaSpecificationExecutor<Type>{

}
