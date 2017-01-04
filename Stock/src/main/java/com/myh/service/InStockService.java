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

import com.myh.domain.Good;
import com.myh.domain.InStock;
import com.myh.domain.InStockItem;
import com.myh.domain.JGPage;
import com.myh.domain.User;
import com.myh.exception.CustomException;
import com.myh.repository.InStockItemRepository;
import com.myh.repository.InStockRepository;

@Service
public class InStockService implements BaseService<InStock, String>{

	
	private static final Log log = LogFactory.getLog(InStockService.class);
	
	@Autowired
	private InStockRepository inStockRepository;
	@Autowired
	private InStockItemRepository inStockItemRepository;

	
	@Transactional
	@Override
	public void save(InStock instock) throws Exception {
		try{
			inStockRepository.save(instock);
		}catch(Exception e){
			throw new CustomException("instockId:"+instock.getId()+"插入失败！why:"+e.getMessage());
		}
	}

	@Transactional
	public void saveItems(InStockItem inStockItem) throws Exception{
		try{
			inStockItemRepository.save(inStockItem);
		}catch(Exception e){
			throw new CustomException("插入失败！why:"+e.getMessage());
		}
	}
	
	@Transactional
	@Override
	public void edit(InStock instock) throws Exception {
		try{
			inStockRepository.save(instock);
		}catch(Exception e){
			throw new CustomException("instockId:"+instock.getId()+"更新失败！why:"+e.getMessage());
		}
	}

	@Transactional
	@Override
	public void delete(String[] ids) throws Exception {
		for(String instockId : ids){
			try{
				inStockRepository.delete(instockId);
			}catch(Exception e){
				throw new CustomException("instockId:"+instockId+"删除失败！why:"+e.getMessage());
			}
		}	
	}
	
	@Override
	public InStock findOne(String id) throws Exception {
		InStock instock = inStockRepository.findOne(id);
		if(instock == null){
			throw new CustomException("查询不到此单据！");
		}
		return instock;
	}

	@Override
	public JGPage<InStock> finaAll(final InStock instock, Pageable pageable)
			throws Exception {
		Page<InStock> instocks = inStockRepository.findAll(new Specification<InStock>(){
			@Override
			public Predicate toPredicate(Root<InStock> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				if(instock != null){
					List<Predicate> andPredicates = new ArrayList<Predicate>();  
					
					Predicate idLike = null;
					if(!StringUtils.isEmpty(instock.getId())){
						idLike = cb.like(root.<String> get("id"), "%"+instock.getId()+"%");
						andPredicates.add(idLike);
					}
					Predicate typeEqual = null;
					if(!StringUtils.isEmpty(instock.getType()) && !instock.getType().equals("无")){
						typeEqual = cb.equal(root.<String> get("type"), instock.getType());
						andPredicates.add(typeEqual);
					}
					Predicate dateEqual = null;
					if(instock.getDate() != null){
						if(instock.getRemark() != null && instock.getRemark().equals("eq")){
							dateEqual = cb.equal(root.<Date> get("date"), instock.getDate());
						}
						if(instock.getRemark() != null && instock.getRemark().equals("le")){
							dateEqual = cb.lessThanOrEqualTo(root.<Date> get("date"), instock.getDate());
						}
						if(instock.getRemark() != null && instock.getRemark().equals("ge")){
							dateEqual = cb.greaterThanOrEqualTo(root.<Date> get("date"), instock.getDate());
						}
						andPredicates.add(dateEqual);
					}
					Predicate userEqual = null;
					if(instock.getUser()  != null){
						userEqual = cb.equal(root.<User> get("user"), instock.getUser());
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
		JGPage<InStock> instocksPage = new JGPage<InStock>(instocks.getTotalElements(), instocks.getNumber()+1, instocks.getTotalPages(), instocks.getContent());
		return instocksPage;
	}

	
	public JGPage<InStockItem> findOneInItems(final String instockId, Pageable pageable)
			throws Exception{
		if(StringUtils.isEmpty(instockId)){
			throw new CustomException("没有获取到参数！");
		}
		Page<InStockItem> instockItems = inStockItemRepository.findAll(
				new Specification<InStockItem>(){
					@Override
					public Predicate toPredicate(Root<InStockItem> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate idEqual = null;
						idEqual = cb.equal(root.<String> get("inStock").get("id"), instockId);
						if(null != idEqual) query.where(idEqual);
						return null;
					}
		}, pageable);
		JGPage<InStockItem> itemsPage = new JGPage<InStockItem>(instockItems.getTotalElements(), instockItems.getNumber()+1, instockItems.getTotalPages(), instockItems.getContent()); 
		return itemsPage;
	}
	
	public List<InStockItem> findOneInItems(final String instockId) throws Exception{
		if(StringUtils.isEmpty(instockId)){
			throw new CustomException("没有获取到参数！");
		}
		List<InStockItem> inStockItems = inStockItemRepository.findAll(new Specification<InStockItem>() {

			@Override
			public Predicate toPredicate(Root<InStockItem> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate idEqual = null;
				idEqual = cb.equal(root.<String> get("inStock").get("id"), instockId);
				if(null != idEqual) query.where(idEqual);
				return null;
			}
		});
		return inStockItems;
	}
	
	
	public Long countAll(){
		return inStockRepository.count();
	}
	
    public List<InStockItem> findByToday(){
    	List<InStockItem> instockItems = inStockItemRepository.findAll(new Specification<InStockItem>() {
			@Override
			public Predicate toPredicate(Root<InStockItem> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
				predicate = cb.equal(root.<Date> get("inStock").get("date"), new Date(System.currentTimeMillis()));
				query.where(predicate);
				return null;
			}
		});
    	return instockItems;
    }
}
