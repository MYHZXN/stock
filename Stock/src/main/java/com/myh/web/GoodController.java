package com.myh.web;


import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myh.domain.CustomResponse;
import com.myh.domain.Good;
import com.myh.domain.Type;
import com.myh.domain.JGPage;
import com.myh.domain.Unit;
import com.myh.domain.form.GoodForm;
import com.myh.domain.form.GoodSameForm;
import com.myh.repository.TypeRepository;
import com.myh.repository.UnitRepository;
import com.myh.service.GoodService;

@Controller
@RequestMapping("/good")
public class GoodController {

	private static final Log log = LogFactory.getLog(GoodController.class);
	
	@Autowired
	private GoodService goodService;
	@Autowired 
	private UnitRepository unitRepository;
	@Autowired 
	private TypeRepository typeRepository;
	
	
	@RequestMapping("/findAll")
	public @ResponseBody JGPage<Good> findAll(
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort); 
		return goodService.finaAll(null, pageable);
	}
	
	@RequestMapping(value="/findByCondition", method=RequestMethod.GET)
	public @ResponseBody JGPage<Good> findByCondition(@ModelAttribute GoodForm goodForm,
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		Good good = new Good();
		BeanUtils.copyProperties(goodForm, good);
		good.setIn_price((int)(goodForm.getIn_price()*100));
		good.setOut_price((int)(goodForm.getOut_price()*100));
//		log.info(good.getIn_price());
//		log.info(good.getOut_price());
//		log.info(good.getCur_stock());
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort); 
		return goodService.finaAll(good, pageable);
	}
	
	@RequestMapping("/searchGood")
	public String chooseCondition(){
		return "goodsearch";
	}
	
	@RequestMapping("/showGoodList")
	public String showGoodList(){
		return "good";
	}
	
	@RequestMapping(value="/addGood", method=RequestMethod.GET)
	public String newGoodInfo(Model model){
		model.addAttribute("goodForm", new GoodForm());
		model.addAttribute("units", unitRepository.findAll());
		model.addAttribute("types", typeRepository.findAll());
		return "goodInfo";
	}
	
	@RequestMapping(value="/addGood", method=RequestMethod.POST)
	public String addNewGood(@Valid GoodForm goodForm, BindingResult bindingResult,Model model) throws Exception{
		
        if (bindingResult.hasErrors()) {
    		model.addAttribute("units", unitRepository.findAll());
    		model.addAttribute("types", typeRepository.findAll());
            return "goodInfo";
        }
		
		Good good = new Good();
		BeanUtils.copyProperties(goodForm, good);
		if(goodForm.getId().equals("")){
			good.setId("GD"+System.currentTimeMillis());
		}
		Type type = null;
		if(goodForm.getType()!=0){
			type = typeRepository.findOne(goodForm.getType());
		}
		good.setType(type);
		Unit unit = null;
		if(goodForm.getMeasure_unit()!=0){
			unit = unitRepository.findOne(goodForm.getMeasure_unit());
		}
		good.setMeasure_unit(unit);
		good.setIn_price((int)(goodForm.getIn_price()*100));
		good.setOut_price((int)(goodForm.getOut_price()*100));
		goodService.save(good);
		return "addsuccess";
	}
	
	@RequestMapping(value="/editGood/{id}", method=RequestMethod.GET)
	public String editGoodInfo(@PathVariable("id") String id,Model model) throws Exception{
		Good good = goodService.findOne(id);
		model.addAttribute("good", good);
		GoodForm goodForm = new GoodForm();
		BeanUtils.copyProperties(good, goodForm);
		goodForm.setIn_price(good.getIn_price()/100);
		goodForm.setOut_price(good.getOut_price()/100);
		goodForm.setMeasure_unit(good.getMeasure_unit().getId());
		if(good.getType()!=null){
			goodForm.setType(good.getType().getId());
		}
		model.addAttribute("goodForm", goodForm);
		model.addAttribute("units", unitRepository.findAll());
		model.addAttribute("types", typeRepository.findAll());
		return "goodInfo";
	}
	
	@RequestMapping(value="/editGood/{id}", method=RequestMethod.POST)
	public String updateOldGood(@PathVariable("id") String id,@ModelAttribute GoodForm goodForm, Model model) throws Exception{
		Good good = goodService.findOne(id);
		BeanUtils.copyProperties(goodForm, good);
		Type type = null;
		if(goodForm.getType()!=0){
			type = typeRepository.findOne(goodForm.getType());
		}
		good.setType(type);
		Unit unit = null;
		if(goodForm.getMeasure_unit()!=0){
			unit = unitRepository.findOne(goodForm.getMeasure_unit());
		}
		good.setMeasure_unit(unit);
		good.setIn_price((int)(goodForm.getIn_price()*100));
		good.setOut_price((int)(goodForm.getOut_price()*100));
		goodService.save(good);
		return "addsuccess";
	}
	
	@RequestMapping(value="/deleteGoods", method=RequestMethod.GET)
	public @ResponseBody CustomResponse deleteGoods(@RequestParam("ids") String ids) throws Exception{
		String[] deleteIds = ids.split(",");
		goodService.delete(deleteIds);
		return new CustomResponse("200","删除成功！");
	}
	
	
	@RequestMapping("/showTypeList")
	public String showTypeList(){
		return "goodtype";
	}
	
	@RequestMapping("/findAllType")
	public @ResponseBody JGPage<Type> findAllType(
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort); 
		return goodService.finaAllType(pageable);
	}
	
	@RequestMapping(value="/addType", method=RequestMethod.GET)
	public String newTypeInfo(Model model){
		model.addAttribute("goodSameForm",new GoodSameForm());
		model.addAttribute("which", "type");
		return "goodsameInfo";
	}

	@RequestMapping(value="/addType", method=RequestMethod.POST)
	public String addNewType(@Valid GoodSameForm goodSameForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "goodsameInfo";
        }
        Type type = new Type();
        type.setName(goodSameForm.getName());
        typeRepository.save(type);
		return "addsuccess";
	}
	
	@RequestMapping(value="/deleteTypes", method=RequestMethod.GET)
	public @ResponseBody CustomResponse deleteTypes(@RequestParam("ids") String ids) throws Exception{
		String[] deleteIds = ids.split(",");
		goodService.deleteTypes(deleteIds);
		return new CustomResponse("200","删除成功！");
	}
	
	@RequestMapping("/showUnitList")
	public String showUnitList(){
		return "goodunit";
	}
	
	@RequestMapping("/findAllUnit")
	public @ResponseBody JGPage<Unit> findAllUnit(
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort); 
		return goodService.finaAllUnit(pageable);
	}
	
	
	@RequestMapping(value="/addUnit", method=RequestMethod.GET)
	public String newUnitInfo(Model model){
		model.addAttribute("goodSameForm",new GoodSameForm());
		return "goodsameInfo";
	}

	@RequestMapping(value="/addUnit", method=RequestMethod.POST)
	public String addNewUnit(@Valid GoodSameForm goodSameForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "goodsameInfo";
        }
        Unit unit = new Unit();
        unit.setName(goodSameForm.getName());
        unitRepository.save(unit);
        return "addsuccess";
	}
	
	@RequestMapping(value="/deleteUnits", method=RequestMethod.GET)
	public @ResponseBody CustomResponse deleteUnits(@RequestParam("ids") String ids) throws Exception{
		String[] deleteIds = ids.split(",");
		goodService.deleteUnits(deleteIds);
		return new CustomResponse("200","删除成功！");
	}
}
