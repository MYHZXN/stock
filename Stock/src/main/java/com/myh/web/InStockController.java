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
import com.myh.domain.InStock;
import com.myh.domain.InStockItem;
import com.myh.domain.JGPage;
import com.myh.domain.User;
import com.myh.domain.form.InStockForm;
import com.myh.service.GoodService;
import com.myh.service.InStockService;
import com.myh.service.UserService;


@Controller
@RequestMapping("/instock")
public class InStockController {
	
	private static final Log log = LogFactory.getLog(InStockController.class);
	
	@Autowired
	private InStockService inStockService;
	@Autowired
	private UserService userService;
	@Autowired
	private GoodService goodService;

	@RequestMapping("/showInStockList")
	public String showInStockList(){
		return "instock";
	}
	
	@RequestMapping("/searchInStock")
	public String chooseCondition(Model model){
		model.addAttribute("users", userService.findAll());
		return "instocksearch";
	}
	
	
	@RequestMapping("/findOneDocuments/{id}")
	public String findOneDocuments(@PathVariable("id") String id,Model model) throws Exception{
		InStock inStock = inStockService.findOne(id);
		String[] documentsSrc = inStock.getDocuments().split("#");
		model.addAttribute("documentsSrc", documentsSrc);
		return "documents";
	}
	
	
	@RequestMapping("/findAll")
	public @ResponseBody JGPage<InStock> findAll(
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort); 
		return inStockService.finaAll(null, pageable);
	}
	
	@RequestMapping(value="/findByCondition",method=RequestMethod.GET)
	public @ResponseBody JGPage<InStock> findByCondition(@ModelAttribute InStockForm inStockForm,
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		InStock inStock = new InStock();
		BeanUtils.copyProperties(inStockForm, inStock);
		if(!inStockForm.getDate().isEmpty()){
			inStock.setDate(Date.valueOf(inStockForm.getDate()));
		}
		if(!inStockForm.getUser().isEmpty() && !inStockForm.getUser().equals("0")){
			User user = userService.findOne(inStockForm.getUser());
			inStock.setUser(user);
		}
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort); 
		return inStockService.finaAll(inStock, pageable);
	}
	
	
	@RequestMapping("/showOneInItems")
	public @ResponseBody JGPage<InStockItem> findOneInItems(
			@RequestParam(value = "instockId") String instockId,
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort);
		return inStockService.findOneInItems(instockId, pageable);
	}
	
	@RequestMapping("/selectGood")
	public String selectGood(){
		return "goodselect";
	}
	
	@RequestMapping(value="/addInStock", method=RequestMethod.GET)
	public String newInStockInfo(Model model){
		model.addAttribute("instockForm", new InStockForm());
		model.addAttribute("users", userService.findAll());
		return "instockInfo";
	}
	
	@RequestMapping(value="/addInStock", method=RequestMethod.POST)
	public String addNewInStock(@ModelAttribute InStockForm inStockForm,Model model){
		model.addAttribute("instockForm", inStockForm);
		model.addAttribute("which", "instock");
		return "documentsUpload";
	}
	
	@RequestMapping(value="/addRealInStock", method=RequestMethod.POST)
	public String addRealInStock(@ModelAttribute InStockForm inStockForm,Model model) throws Exception{
		InStock inStock = new InStock();
		BeanUtils.copyProperties(inStockForm, inStock);
		inStock.setId("RK"+System.currentTimeMillis());
		inStock.setDate(Date.valueOf(inStockForm.getDate()));
		User user = userService.findOne(inStockForm.getUser());
		inStock.setUser(user);
		inStockService.save(inStock);
		//Items
		String[] goodIds = inStockForm.getGoodIds();
		String[] nums = inStockForm.getNums();
		String[] worths = inStockForm.getWorths();
		for(int i = 0; i < inStockForm.getGoodIds().length; i++){
			InStockItem inStockItem = new InStockItem();
			Good good = goodService.findOne(goodIds[i]);
			good.setCur_stock(good.getCur_stock()+Integer.parseInt(nums[i]));
			goodService.save(good);
			inStockItem.setGood(good);
			inStockItem.setInStock(inStock);
			inStockItem.setNum(Integer.parseInt(nums[i]));
			float worth = Float.parseFloat(worths[i])*100;
			inStockItem.setWorth((int)worth);
			inStockService.saveItems(inStockItem);
		}
		return "addsuccess";
	}
	
	
	@RequestMapping(value="/deleteInStocks", method=RequestMethod.GET)
	public @ResponseBody CustomResponse deleteInStocks(@RequestParam("ids") String ids) throws Exception{
		String[] deleteIds = ids.split(",");
		inStockService.delete(deleteIds);
		return new CustomResponse("200", "删除成功!");
	}
}
