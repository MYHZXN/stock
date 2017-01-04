package com.myh.service;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;

import com.myh.domain.JGPage;

public interface BaseService<T,ID extends Serializable> {

	void save(T t) throws Exception;
	void edit(T t) throws Exception;
	void delete(ID[] ids) throws Exception;
	T findOne(ID id) throws Exception;
	JGPage<T> finaAll(T t,Pageable pageable) throws Exception;
}
