package com.myh.web;

import java.sql.Date;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myh.domain.CustomResponse;
import com.myh.domain.Good;
import com.myh.domain.JGPage;
import com.myh.domain.OutStock;
import com.myh.domain.OutStockItem;
import com.myh.domain.User;
import com.myh.domain.form.InStockForm;
import com.myh.service.GoodService;
import com.myh.service.OutStockService;
import com.myh.service.UserService;


@Controller
@RequestMapping("/outstock")
public class OutStockController {
	
	private static final Log log = LogFactory.getLog(OutStockController.class);
	
	@Autowired
	private OutStockService outStockService;
	@Autowired
	private UserService userService;
	@Autowired
	private GoodService goodService;

	@RequestMapping("/showOutStockList")
	public String showInStockList(){
		return "outstock";
	}
	
	@RequestMapping("/searchOutStock")
	public String chooseCondition(Model model){
		model.addAttribute("users", userService.findAll());
		return "outstocksearch";
	}
	
	
	@RequestMapping("/findOneDocuments/{id}")
	public String findOneDocuments(@PathVariable("id") String id,Model model) throws Exception{
		OutStock outStock = outStockService.findOne(id);
		String[] documentsSrc = outStock.getDocuments().split("#");
		model.addAttribute("documentsSrc", documentsSrc);
		return "documents";
	}
	
	
	@RequestMapping("/findAll")
	public @ResponseBody JGPage<OutStock> findAll(
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort); 
		return outStockService.finaAll(null, pageable);
	}
	
	@RequestMapping(value="/findByCondition",method=RequestMethod.GET)
	public @ResponseBody JGPage<OutStock> findByCondition(@ModelAttribute InStockForm inStockForm,
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		OutStock outStock = new OutStock();
		BeanUtils.copyProperties(inStockForm, outStock);
		if(!inStockForm.getDate().isEmpty()){
			outStock.setDate(Date.valueOf(inStockForm.getDate()));
		}
		if(!inStockForm.getUser().isEmpty() && !inStockForm.getUser().equals("0")){
			User user = userService.findOne(inStockForm.getUser());
			outStock.setUser(user);
		}
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort); 
		return outStockService.finaAll(outStock, pageable);
	}
	
	
	@RequestMapping("/showOneInItems")
	public @ResponseBody JGPage<OutStockItem> findOneInItems(
			@RequestParam(value = "outstockId") String outstockId,
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort);
		return outStockService.findOneInItems(outstockId, pageable);
	}
	
	@RequestMapping("/selectGood")
	public String selectGood(Model model){
		model.addAttribute("inOrOut", "out");
		return "goodselect";
	}
	
	@RequestMapping(value="/addOutStock", method=RequestMethod.GET)
	public String newInStockInfo(Model model){
		model.addAttribute("instockForm", new InStockForm());
		model.addAttribute("users", userService.findAll());
		return "outstockInfo";
	}
	
	@RequestMapping(value="/addOutStock", method=RequestMethod.POST)
	public String addNewInStock(@ModelAttribute InStockForm inStockForm,Model model){
		model.addAttribute("instockForm", inStockForm);
		return "documentsUpload";
	}
	
	@RequestMapping(value="/addRealOutStock", method=RequestMethod.POST)
	public String addRealInStock(@ModelAttribute InStockForm inStockForm,Model model) throws Exception{
		OutStock outStock = new OutStock();
		BeanUtils.copyProperties(inStockForm, outStock);
		outStock.setId("CK"+System.currentTimeMillis());
		outStock.setDate(Date.valueOf(inStockForm.getDate()));
		User user = userService.findOne(inStockForm.getUser());
		outStock.setUser(user);
		outStockService.save(outStock);
		//Items
		String[] goodIds = inStockForm.getGoodIds();
		String[] nums = inStockForm.getNums();
		String[] worths = inStockForm.getWorths();
		for(int i = 0; i < inStockForm.getGoodIds().length; i++){
			OutStockItem outStockItem = new OutStockItem();
			Good good = goodService.findOne(goodIds[i]);
			good.setCur_stock(good.getCur_stock()-Integer.parseInt(nums[i]));
			goodService.save(good);
			outStockItem.setGood(goodService.findOne(goodIds[i]));
			outStockItem.setOutStock(outStock);
			outStockItem.setNum(Integer.parseInt(nums[i]));
			float worth = Float.parseFloat(worths[i])*100;
			outStockItem.setWorth((int)worth);
			outStockService.saveItems(outStockItem);
		}
		return "addsuccess";
	}
	
	
	@RequestMapping(value="/deleteOutStocks", method=RequestMethod.GET)
	public @ResponseBody CustomResponse deleteInStocks(@RequestParam("ids") String ids) throws Exception{
		String[] deleteIds = ids.split(",");
		outStockService.delete(deleteIds);
		return new CustomResponse("200", "删除成功!");
	}
}
