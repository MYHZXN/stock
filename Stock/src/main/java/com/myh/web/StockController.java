package com.myh.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.myh.service.GoodService;
import com.myh.service.InStockService;
import com.myh.service.OutStockService;
import com.myh.service.ReportsService;

@RequestMapping("/stock")
@Controller
public class StockController {

	@Autowired
	private GoodService goodService;
	@Autowired
	private InStockService inStockService;
	@Autowired
	private OutStockService outStockService;
	@Autowired
	private ReportsService reportsService;
	
	@RequestMapping("index")
	public String showIndex(Model model){
		model.addAttribute("goodcount", goodService.countAll());
		model.addAttribute("lessgoodcount", goodService.countByCurStock());
		model.addAttribute("instockcount", inStockService.countAll());
		model.addAttribute("outstockcount", outStockService.countAll());
		model.addAttribute("stockworth", Integer.parseInt(reportsService.calStockWorth()));
		model.addAttribute("todayInstockItems", inStockService.findByToday());
		model.addAttribute("todayOutstockItems", outStockService.findByToday());
		return "index";
	}
	
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
	
	@RequestMapping("/404")
	public String showNotFoundPage(){
		return "404";
	}
	
	@RequestMapping("/403")
	public String showForbidden(){
		return "403";
	}
	
	@RequestMapping("/500")
	public String showInternalServerError(){
		return "500";
	}
}
