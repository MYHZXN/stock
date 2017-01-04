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

import com.myh.domain.InStock;
import com.myh.domain.OutStock;
import com.myh.domain.OutStockItem;
import com.myh.domain.JGPage;
import com.myh.domain.User;
import com.myh.exception.CustomException;
import com.myh.repository.OutStockItemRepository;
import com.myh.repository.OutStockRepository;

@Service
public class OutStockService implements BaseService<OutStock, String>{

	
	private static final Log log = LogFactory.getLog(OutStockService.class);
	
	@Autowired
	private OutStockRepository outStockRepository;
	@Autowired
	private OutStockItemRepository outStockItemRepository;

	
	@Transactional
	@Override
	public void save(OutStock outStock) throws Exception {
		try{
			outStockRepository.save(outStock);
		}catch(Exception e){
			throw new CustomException("instockId:"+outStock.getId()+"插入失败！why:"+e.getMessage());
		}
	}

	@Transactional
	public void saveItems(OutStockItem outStockItem) throws Exception{
		try{
			outStockItemRepository.save(outStockItem);
		}catch(Exception e){
			throw new CustomException("插入失败！why:"+e.getMessage());
		}
	}
	
	@Transactional
	@Override
	public void edit(OutStock outStock) throws Exception {
		try{
			outStockRepository.save(outStock);
		}catch(Exception e){
			throw new CustomException("instockId:"+outStock.getId()+"更新失败！why:"+e.getMessage());
		}
	}

	@Transactional
	@Override
	public void delete(String[] ids) throws Exception {
		for(String outstockId : ids){
			try{
				outStockRepository.delete(outstockId);
			}catch(Exception e){
				throw new CustomException("instockId:"+outstockId+"删除失败！why:"+e.getMessage());
			}
		}	
	}
	
	@Override
	public OutStock findOne(String id) throws Exception {
		OutStock outStock = outStockRepository.findOne(id);
		if(outStock == null){
			throw new CustomException("查询不到此单据！");
		}
		return outStock;
	}

	@Override
	public JGPage<OutStock> finaAll(final OutStock outStock, Pageable pageable)
			throws Exception {
		Page<OutStock> instocks = outStockRepository.findAll(new Specification<OutStock>(){
			@Override
			public Predicate toPredicate(Root<OutStock> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				if(outStock != null){
					List<Predicate> andPredicates = new ArrayList<Predicate>();  
					
					Predicate idLike = null;
					if(!StringUtils.isEmpty(outStock.getId())){
						idLike = cb.like(root.<String> get("id"), "%"+outStock.getId()+"%");
						andPredicates.add(idLike);
					}
					Predicate typeEqual = null;
					if(!StringUtils.isEmpty(outStock.getType()) && !outStock.getType().equals("无")){
						typeEqual = cb.equal(root.<String> get("type"), outStock.getType());
						andPredicates.add(typeEqual);
					}
					Predicate dateEqual = null;
					if(outStock.getDate() != null){
						if(outStock.getRemark() != null && outStock.getRemark().equals("eq")){
							dateEqual = cb.equal(root.<Date> get("date"), outStock.getDate());
						}
						if(outStock.getRemark() != null && outStock.getRemark().equals("le")){
							dateEqual = cb.lessThanOrEqualTo(root.<Date> get("date"), outStock.getDate());
						}
						if(outStock.getRemark() != null && outStock.getRemark().equals("ge")){
							dateEqual = cb.greaterThanOrEqualTo(root.<Date> get("date"), outStock.getDate());
						}
						andPredicates.add(dateEqual);
					}
					Predicate userEqual = null;
					if(outStock.getUser()  != null){
						userEqual = cb.equal(root.<User> get("user"), outStock.getUser());
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
		JGPage<OutStock> instocksPage = new JGPage<OutStock>(instocks.getTotalElements(), instocks.getNumber()+1, instocks.getTotalPages(), instocks.getContent());
		return instocksPage;
	}

	
	public JGPage<OutStockItem> findOneInItems(final String outstockId, Pageable pageable)
			throws Exception{
		if(StringUtils.isEmpty(outstockId)){
			throw new CustomException("没有获取到参数！");
		}
		Page<OutStockItem> outstockItems = outStockItemRepository.findAll(
				new Specification<OutStockItem>(){
					@Override
					public Predicate toPredicate(Root<OutStockItem> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate idEqual = null;
						idEqual = cb.equal(root.<String> get("outStock").get("id"), outstockId);
						if(null != idEqual) query.where(idEqual);
						return null;
					}
		}, pageable);
		JGPage<OutStockItem> itemsPage = new JGPage<OutStockItem>(outstockItems.getTotalElements(), outstockItems.getNumber()+1, outstockItems.getTotalPages(), outstockItems.getContent()); 
		return itemsPage;
	}
	
	public List<OutStockItem> findOneOutItems(final String outstockId) throws Exception{
		if(StringUtils.isEmpty(outstockId)){
			throw new CustomException("没有获取到参数！");
		}
		List<OutStockItem> outStockItems = outStockItemRepository.findAll(
				new Specification<OutStockItem>(){
					@Override
					public Predicate toPredicate(Root<OutStockItem> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate idEqual = null;
						idEqual = cb.equal(root.<String> get("outStock").get("id"), outstockId);
						if(null != idEqual) query.where(idEqual);
						return null;
					}
		});
		return outStockItems;
	}
	
	public Long countAll(){
		return outStockRepository.count();
	}
	
    public List<OutStockItem> findByToday(){
    	List<OutStockItem> outstockItems = outStockItemRepository.findAll(new Specification<OutStockItem>() {
			@Override
			public Predicate toPredicate(Root<OutStockItem> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
				predicate = cb.equal(root.<Date> get("outStock").get("date"), new Date(System.currentTimeMillis()));
				query.where(predicate);
				return null;
			}
		});
    	return outstockItems;
    }
}
