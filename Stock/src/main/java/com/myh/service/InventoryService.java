package com.myh.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.myh.domain.Inventory;
import com.myh.domain.InventoryItem;
import com.myh.domain.JGPage;
import com.myh.domain.User;
import com.myh.exception.CustomException;
import com.myh.repository.InventoryItemRepository;
import com.myh.repository.InventoryReository;

@Service
public class InventoryService implements BaseService<Inventory, String>{

	private static final Log log = LogFactory.getLog(InventoryService.class);
	
	@Autowired
	private InventoryReository inventoryReository;
	@Autowired
	private InventoryItemRepository inventoryItemRepository;
	
	@Transactional
	@Override
	public void save(Inventory inventory) throws Exception {
		try{
			inventoryReository.save(inventory);
		}catch(Exception e){
			throw new CustomException("inventoryId:"+inventory.getId()+"插入失败！why:"+e.getMessage());
		}
	}

	@Transactional
	public void saveItems(InventoryItem inventoryItem) throws Exception{
		try{
			inventoryItemRepository.save(inventoryItem);
		}catch(Exception e){
			throw new CustomException("插入失败！why:"+e.getMessage());
		}
	}
	
	@Transactional
	@Override
	public void edit(Inventory inventory) throws Exception {
		try{
			inventoryReository.save(inventory);
		}catch(Exception e){
			throw new CustomException("inventoryId:"+inventory.getId()+"更新失败！why:"+e.getMessage());
		}		
	}

	@Transactional
	@Override
	public void delete(String[] ids) throws Exception {
		for(String inventoryId : ids){
			try{
				inventoryReository.delete(inventoryId);
			}catch(Exception e){
				throw new CustomException("inventoryId:"+inventoryId+"删除失败！why:"+e.getMessage());
			}
		}	
	}

	@Override
	public Inventory findOne(String id) throws Exception {
		Inventory inventory = inventoryReository.findOne(id);
		if(inventory == null){
			throw new CustomException("查询不到此单据！");
		}
		return inventory;
	}

	@Override
	public JGPage<Inventory> finaAll(final Inventory inventory, Pageable pageable)
			throws Exception {
		Page<Inventory> inventorys = inventoryReository.findAll(new Specification<Inventory>(){
			@Override
			public Predicate toPredicate(Root<Inventory> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				if(inventory != null){
					List<Predicate> andPredicates = new ArrayList<Predicate>();  
					Predicate idLike = null;
					if(!StringUtils.isEmpty(inventory.getId())){
						idLike = cb.like(root.<String> get("id"), "%"+inventory.getId()+"%");
						andPredicates.add(idLike);
					}
					Predicate dateEqual = null;
					if(inventory.getDate() != null){
						if(inventory.getRemark() != null && inventory.getRemark().equals("eq")){
							dateEqual = cb.equal(root.<Date> get("date"), inventory.getDate());
						}
						if(inventory.getRemark() != null && inventory.getRemark().equals("le")){
							dateEqual = cb.lessThanOrEqualTo(root.<Date> get("date"), inventory.getDate());
						}
						if(inventory.getRemark() != null && inventory.getRemark().equals("ge")){
							dateEqual = cb.greaterThanOrEqualTo(root.<Date> get("date"), inventory.getDate());
						}
						andPredicates.add(dateEqual);
					}
					Predicate userEqual = null;
					if(inventory.getUser()  != null){
						userEqual = cb.equal(root.<User> get("user"), inventory.getUser());
						andPredicates.add(userEqual);
					}
					
					Predicate p = cb.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
					query.where(p);
//					if(idLike != null) query.where(idLike);
//					if(typeEqual != null) query.where(typeEqual);
//					if(dateEqual != null) query.where(dateEqual);
//					if(userEqual != null) query.where(userEqual);
				}
				return null;
			}
			
		},pageable);
		JGPage<Inventory> inventorysPage = new JGPage<Inventory>(inventorys.getTotalElements(), inventorys.getNumber()+1, inventorys.getTotalPages(), inventorys.getContent());
		return inventorysPage;
	}

	public JGPage<InventoryItem> findOneInItems(final String inventoryId, Pageable pageable)
			throws Exception{
		if(StringUtils.isEmpty(inventoryId)){
			throw new CustomException("没有获取到参数！");
		}
		Page<InventoryItem> inventoryItems = inventoryItemRepository.findAll(
				new Specification<InventoryItem>(){
					@Override
					public Predicate toPredicate(Root<InventoryItem> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate idEqual = null;
						idEqual = cb.equal(root.<String> get("inventory").get("id"), inventoryId);
						if(null != idEqual) query.where(idEqual);
						return null;
					}
		}, pageable);
		JGPage<InventoryItem> itemsPage = new JGPage<InventoryItem>(inventoryItems.getTotalElements(), inventoryItems.getNumber()+1, inventoryItems.getTotalPages(), inventoryItems.getContent()); 
		return itemsPage;
	}
	
}
