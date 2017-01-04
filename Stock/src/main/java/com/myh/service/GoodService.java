package com.myh.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.myh.domain.Good;
import com.myh.domain.JGPage;
import com.myh.domain.Type;
import com.myh.domain.Unit;
import com.myh.exception.CustomException;
import com.myh.repository.GoodRepository;
import com.myh.repository.TypeRepository;
import com.myh.repository.UnitRepository;

@Service
public class GoodService implements BaseService<Good, String>{

	@Autowired
	private GoodRepository goodRepository;
	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private UnitRepository unitRepository;
	
	
	@Transactional
	@Override
	public void save(Good good) throws Exception {
		try{
			goodRepository.save(good);
		}catch(Exception e){
			throw new CustomException("goodId:"+good.getId()+"插入失败！why:"+e.getMessage());
		}
	}

	@Transactional
	@Override
	public void edit(Good good) throws Exception {
		try{
			goodRepository.save(good);
		}catch(Exception e){
			throw new CustomException("goodId:"+good.getId()+"更新失败！why:"+e.getMessage());
		}
	}

	@Transactional
	@Override
	public void delete(String[] ids) throws Exception {
		for(String goodId : ids){
			try{
				goodRepository.delete(goodId);
			}catch(Exception e){
				throw new CustomException("goodId:"+goodId+"删除失败！why:"+e.getMessage());
			}
		}		
	}

	@Override
	public Good findOne(String id) throws Exception {
		Good good = goodRepository.findOne(id);
		if(good == null){
			throw new CustomException("查询不到此商品！");
		}
		return good;
	}

	
	@Transactional
	public void saveType(Type type) throws Exception {
		try{
			typeRepository.save(type);
		}catch(Exception e){
			throw new CustomException("typeName:"+type.getName()+"插入失败！why:"+e.getMessage());
		}
	}

	@Transactional
	public void editType(Type type) throws Exception {
		try{
			typeRepository.save(type);
		}catch(Exception e){
			throw new CustomException("typeId:"+type.getId()+"更新失败！why:"+e.getMessage());
		}
	}

	@Transactional
	public void deleteTypes(String[] ids) throws Exception {
		for(String typeId : ids){
			try{
				typeRepository.delete(Integer.parseInt(typeId));
			}catch(Exception e){
				throw new CustomException("typeId:"+typeId+"删除失败！why:"+e.getMessage());
			}
		}		
	}

	
	@Transactional
	public void saveUnit(Unit unit) throws Exception {
		try{
			unitRepository.save(unit);
		}catch(Exception e){
			throw new CustomException("unitName:"+unit.getName()+"插入失败！why:"+e.getMessage());
		}
	}

	@Transactional
	public void editUnit(Unit unit) throws Exception {
		try{
			unitRepository.save(unit);
		}catch(Exception e){
			throw new CustomException("unitId:"+unit.getId()+"更新失败！why:"+e.getMessage());
		}
	}

	@Transactional
	public void deleteUnits(String[] ids) throws Exception {
		for(String unitId : ids){
			try{
				unitRepository.delete(Integer.parseInt(unitId));
			}catch(Exception e){
				throw new CustomException("unitId:"+unitId+"删除失败！why:"+e.getMessage());
			}
		}		
	}

	
	@Override
	public JGPage<Good> finaAll(final Good good, Pageable pageable) throws Exception {
		
		Page<Good> goods = goodRepository.findAll(new Specification<Good>() {
			@Override
			public Predicate toPredicate(Root<Good> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				if(good != null){
					List<Predicate> andPredicates = new ArrayList<Predicate>(); 
					
					Predicate idLike = null;
					if(!StringUtils.isEmpty(good.getId())){
						idLike = cb.like(root.<String> get("id"), "%"+good.getId()+"%");
						andPredicates.add(idLike);
					}
					Predicate barcodeLike = null;
					if(!StringUtils.isEmpty(good.getBarcode())){
						barcodeLike = cb.like(root.<String> get("barcode"), "%"+good.getBarcode()+"%");
						andPredicates.add(barcodeLike);
					}
					Predicate nameLike = null;
					if(!StringUtils.isEmpty(good.getName())){
						nameLike = cb.like(root.<String> get("name"), "%"+good.getName()+"%");
						andPredicates.add(nameLike);
					}
					Predicate stockQuery = null;
					if(good.getCur_stock()!=-1){
						if(!StringUtils.isEmpty(good.getExpand_1())&&good.getExpand_1().equals("eq")){
							stockQuery = cb.equal(root.<Integer> get("cur_stock"), good.getCur_stock());
						}
						if(!StringUtils.isEmpty(good.getExpand_1())&&good.getExpand_1().equals("ge")){
							stockQuery = cb.ge(root.<Integer> get("cur_stock"), good.getCur_stock());
						}
						if(!StringUtils.isEmpty(good.getExpand_1())&&good.getExpand_1().equals("le")){
							stockQuery = cb.le(root.<Integer> get("cur_stock"), good.getCur_stock());
						}
						andPredicates.add(stockQuery);
					}
					Predicate inPriceQuery = null;
					if(good.getIn_price()!=0){
						if(!StringUtils.isEmpty(good.getExpand_2())&&good.getExpand_2().equals("eq")){
							inPriceQuery = cb.equal(root.<Integer> get("in_price"), good.getIn_price());
						}
						if(!StringUtils.isEmpty(good.getExpand_2())&&good.getExpand_2().equals("ge")){
							inPriceQuery = cb.ge(root.<Integer> get("in_price"), good.getIn_price());
						}
						if(!StringUtils.isEmpty(good.getExpand_2())&&good.getExpand_2().equals("le")){
							inPriceQuery = cb.le(root.<Integer> get("in_price"), good.getIn_price());
						}
						andPredicates.add(inPriceQuery);
					}
					Predicate outPriceQuery = null;
					if(good.getOut_price()!=0){
						if(!StringUtils.isEmpty(good.getExpand_3())&&good.getExpand_3().equals("eq")){
							outPriceQuery = cb.equal(root.<Integer> get("out_price"), good.getOut_price());
						}
						if(!StringUtils.isEmpty(good.getExpand_3())&&good.getExpand_3().equals("ge")){
							outPriceQuery = cb.ge(root.<Integer> get("out_price"), good.getOut_price());
						}
						if(!StringUtils.isEmpty(good.getExpand_3())&&good.getExpand_3().equals("le")){
							outPriceQuery = cb.le(root.<Integer> get("out_price"), good.getOut_price());
						}
						andPredicates.add(outPriceQuery);
					}
					
					Predicate p = cb.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
					query.where(p);
//					if(null != idLike) query.where(idLike);
//			        if(null != barcodeLike) query.where(barcodeLike);
//			        if(null != nameLike) query.where(nameLike);
//			        if(null != stockQuery) query.where(stockQuery);
//			        if(null != inPriceQuery) query.where(inPriceQuery);
//			        if(null != outPriceQuery) query.where(outPriceQuery);
				}
				return null;                
			}
		}, pageable);
		JGPage<Good> goodsPage = new JGPage<Good>(goods.getTotalElements(), goods.getNumber()+1, goods.getTotalPages(), goods.getContent()); 
		return goodsPage;
	}

	
	public Good findByBarcode(final String barcode){
		Good good = goodRepository.findOne(new Specification<Good>() {
			@Override
			public Predicate toPredicate(Root<Good> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate barcodeEqual = null;
				barcodeEqual = cb.equal(root.<String> get("barcode"), barcode);
				query.where(barcodeEqual);
				return null;
			}
		});
		return good;
	}
	
	public JGPage<Type> finaAllType(Pageable pageable) throws Exception {
		Page<Type> types = typeRepository.findAll(pageable);
		JGPage<Type> typesPage = new JGPage<Type>(types.getTotalElements(), types.getNumber()+1, types.getTotalPages(), types.getContent());
		return typesPage;
	}
	
	
	public JGPage<Unit> finaAllUnit(Pageable pageable) throws Exception {
		Page<Unit> units = unitRepository.findAll(pageable);
		JGPage<Unit> typesPage = new JGPage<Unit>(units.getTotalElements(), units.getNumber()+1, units.getTotalPages(), units.getContent());
		return typesPage;
	}
	
	public Long countAll(){
		return goodRepository.count();
	}
	
	
	public Long countByCurStock(){
		Long count = goodRepository.count(new Specification<Good>() {
			@Override
			public Predicate toPredicate(Root<Good> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
				predicate = cb.le(root.<Integer> get("cur_stock"), root.<Integer> get("min_stock"));
				query.where(predicate);
				return null;
			}
		});
		return count;
	}
	
}
