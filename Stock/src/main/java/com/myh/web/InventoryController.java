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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myh.domain.CustomResponse;
import com.myh.domain.Good;
import com.myh.domain.Inventory;
import com.myh.domain.InventoryItem;
import com.myh.domain.JGPage;
import com.myh.domain.User;
import com.myh.domain.form.InventoryForm;
import com.myh.service.GoodService;
import com.myh.service.InventoryService;
import com.myh.service.UserService;


@Controller
@RequestMapping("/inventory")
public class InventoryController {

	private static final Log log = LogFactory.getLog(InventoryController.class);
	
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private GoodService goodService;
	
	@RequestMapping("/showInventoryList")
	public String showInventoryList(){
		return "inventory";
	}
	
	
	@RequestMapping("/searchInventory")
	public String chooseCondition(Model model){
		model.addAttribute("users", userService.findAll());
		return "inventorysearch";
	}
	
	@RequestMapping("/findAll")
	public @ResponseBody JGPage<Inventory> findAll(
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort); 
		return inventoryService.finaAll(null, pageable);
	}
	
	@RequestMapping(value="/findByCondition",method=RequestMethod.GET)
	public @ResponseBody JGPage<Inventory> findByCondition(@ModelAttribute InventoryForm inventoryForm,
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		Inventory inventory = new Inventory();
		BeanUtils.copyProperties(inventoryForm, inventory);
		if(!inventoryForm.getDate().isEmpty()){
			inventory.setDate(Date.valueOf(inventoryForm.getDate()));
		}
		if(!inventoryForm.getUser().isEmpty() && !inventoryForm.getUser().equals("0")){
			User user = userService.findOne(inventoryForm.getUser());
			inventory.setUser(user);
		}
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort); 
		return inventoryService.finaAll(inventory, pageable);
	}
	
	@RequestMapping("/showOneInItems")
	public @ResponseBody JGPage<InventoryItem> findOneInItems(
			@RequestParam(value = "inventoryId") String inventoryId,
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort);
		return inventoryService.findOneInItems(inventoryId, pageable);
	}
	
	@RequestMapping("/selectGood")
	public String selectGood(){
		return "inventorygoodselect";
	}
	
	@RequestMapping(value="/addInventory", method=RequestMethod.GET)
	public String newInventoryInfo(Model model){
		model.addAttribute("inventoryForm", new InventoryForm());
		model.addAttribute("users", userService.findAll());
		return "inventoryInfo";
	}
	
	@RequestMapping(value="/addInventory", method=RequestMethod.POST)
	public String addNewInventory(@ModelAttribute InventoryForm inventoryForm,Model model) throws Exception{
		Inventory inventory = new Inventory();
		BeanUtils.copyProperties(inventoryForm, inventory);
		inventory.setId("PD"+System.currentTimeMillis());
		inventory.setDate(Date.valueOf(inventoryForm.getDate()));
		User user = userService.findOne(inventoryForm.getUser());
		inventory.setUser(user);
		inventoryService.save(inventory);
		//Items
		String[] goodIds = inventoryForm.getGoodIds();
		String[] checknums = inventoryForm.getChecknums();
		for(int i = 0; i < inventoryForm.getGoodIds().length; i++){
			InventoryItem inventoryItem = new InventoryItem();
			Good good = goodService.findOne(goodIds[i]);
			inventoryItem.setGood(good);
			inventoryItem.setInventory(inventory);
			inventoryItem.setNum(good.getCur_stock());
			inventoryItem.setChecknum(Integer.parseInt(checknums[i]));
			inventoryService.saveItems(inventoryItem);
		}
		return "addsuccess";
	}
	
	
	@RequestMapping(value="/deleteInventorys", method=RequestMethod.GET)
	public @ResponseBody CustomResponse deleteInventorys(@RequestParam("ids") String ids) throws Exception{
		String[] deleteIds = ids.split(",");
		inventoryService.delete(deleteIds);
		return new CustomResponse("200", "删除成功!");
	}
}
