package com.myh.api;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myh.domain.Good;
import com.myh.domain.JGPage;
import com.myh.domain.Type;
import com.myh.domain.Unit;
import com.myh.domain.form.GoodForm;
import com.myh.repository.TypeRepository;
import com.myh.repository.UnitRepository;
import com.myh.service.GoodService;

@RestController
@RequestMapping("/api/good")
public class GoodApi {
	
	@Autowired
	private GoodService goodService;
	@Autowired
	private UnitRepository unitRepository;
	@Autowired
	private TypeRepository typeRepository;
	
	@RequestMapping("/all/{page}")
	public ApiResult<JGPage<Good>> getGoodList(@PathVariable("page") int page){
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 15, sort);
        ApiResult<JGPage<Good>> apiResult = new ApiResult<JGPage<Good>>();
        JGPage<Good> jgPage = null;
        try{
        	jgPage = goodService.finaAll(null, pageable);
        }catch(Exception e){
        	apiResult.setCode(500);
        	apiResult.setMsg(e.getMessage());
        	apiResult.setObject(null);
        	return apiResult;
        }
    	apiResult.setCode(200);
    	apiResult.setMsg("success");
    	apiResult.setObject(jgPage);
    	return apiResult;
	}
	
	@RequestMapping("/findByName/{key}/{page}")
	public ApiResult<JGPage<Good>> findByName(@PathVariable("key") String key,
			@PathVariable("page") int page){
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 15, sort);
        ApiResult<JGPage<Good>> apiResult = new ApiResult<JGPage<Good>>();
        JGPage<Good> jgPage = null;
        Good good = new Good();
        good.setName(key);
        good.setCur_stock(-1);
        good.setIn_price(0);
        good.setOut_price(0);
        try{
        	jgPage = goodService.finaAll(good, pageable);
        }catch(Exception e){
        	e.printStackTrace();
        	apiResult.setCode(500);
        	apiResult.setMsg(e.getMessage());
        	apiResult.setObject(null);
        	return apiResult;
        }
    	apiResult.setCode(200);
    	apiResult.setMsg("success");
    	apiResult.setObject(jgPage);
    	return apiResult;
	}
	
	
	@RequestMapping("/findByBarCode/{barcode}")
	public ApiResult<Good> findByBarCode(@PathVariable("barcode") String barcode){
        ApiResult<Good> apiResult = new ApiResult<Good>();
        Good good = null;
        try{
        	good = goodService.findByBarcode(barcode);
        }catch(Exception e){
        	e.printStackTrace();
        	apiResult.setCode(500);
        	apiResult.setMsg(e.getMessage());
        	apiResult.setObject(null);
        	return apiResult;
        }
    	apiResult.setCode(200);
    	apiResult.setMsg("success");
    	apiResult.setObject(good);
    	return apiResult;
	}
	
	@RequestMapping("/findOne/{id}")
	public ApiResult<Good> findOne(@PathVariable String id){
		ApiResult<Good> apiResult = new ApiResult<Good>();
		Good good = null;
		try {
			good = goodService.findOne(id);
		} catch (Exception e) {
        	e.printStackTrace();
        	apiResult.setCode(500);
        	apiResult.setMsg(e.getMessage());
        	apiResult.setObject(null);
        	return apiResult;
		}
		apiResult.setCode(200);
		apiResult.setMsg("success");
		apiResult.setObject(good);
		return apiResult;
	}
	
	@RequestMapping("/allUnit")
	public ApiResult<List<Unit>> getUnitList(){
		ApiResult<List<Unit>> apiResult = new ApiResult<List<Unit>>();
		List<Unit> units = new ArrayList<>();
		try {
			units = unitRepository.findAll();
		} catch (Exception e) {
        	e.printStackTrace();
        	apiResult.setCode(500);
        	apiResult.setMsg(e.getMessage());
        	apiResult.setObject(null);
        	return apiResult;
		}
		apiResult.setCode(200);
		apiResult.setMsg("success");
		apiResult.setObject(units);
		return apiResult;
	}
	
	@RequestMapping("/allType")
	public ApiResult<List<Type>> getTypeList(){
		ApiResult<List<Type>> apiResult = new ApiResult<List<Type>>();
		List<Type> types = new ArrayList<>();
		try {
			types = typeRepository.findAll();
		} catch (Exception e) {
        	e.printStackTrace();
        	apiResult.setCode(500);
        	apiResult.setMsg(e.getMessage());
        	apiResult.setObject(null);
        	return apiResult;
		}
		apiResult.setCode(200);
		apiResult.setMsg("success");
		apiResult.setObject(types);
		return apiResult;
	}
	
	@RequestMapping(value="/addGood",method=RequestMethod.POST)
	public ApiResult<Good> addGood(@RequestBody Good good){
		ApiResult<Good> apiResult = new ApiResult<>();
		if(good != null){
			good.setId("GD"+System.currentTimeMillis());
			try {
				goodService.save(good);
			} catch (Exception e) {
				apiResult.setCode(500);
	        	apiResult.setMsg(e.getMessage());
	        	apiResult.setObject(null);
	        	return apiResult;
			}
			apiResult.setCode(200);
	    	apiResult.setMsg("success");
	    	apiResult.setObject(good);
		}else{
			apiResult.setCode(200);
	    	apiResult.setMsg("请求实体为空");
	    	apiResult.setObject(null);
		}
    	return apiResult;
	}
	
	@RequestMapping(value="/addUnit",method=RequestMethod.POST)
	public ApiResult<Unit> addUnit(@RequestBody Unit unit){
		ApiResult<Unit> apiResult = new ApiResult<>();
		try {
			goodService.saveUnit(unit);
		} catch (Exception e) {
			apiResult.setCode(500);
			apiResult.setMsg(e.getMessage());
			apiResult.setObject(null);
		}
		apiResult.setCode(200);
		apiResult.setMsg("success");
		apiResult.setObject(unit);
		return apiResult;
	}
	
	@RequestMapping(value="/addType",method=RequestMethod.POST)
	public ApiResult<Type> addType(@RequestBody Type type){
		ApiResult<Type> apiResult = new ApiResult<>();
		try{
			goodService.saveType(type);
		} catch (Exception e) {
			apiResult.setCode(500);
			apiResult.setMsg(e.getMessage());
			apiResult.setObject(type);
		}
		apiResult.setCode(200);
		apiResult.setMsg("success");
		apiResult.setObject(type);
		return apiResult;
	}
	
}
