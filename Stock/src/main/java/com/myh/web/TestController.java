package com.myh.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.myh.domain.StockDetail;
import com.myh.domain.StockReports;
import com.myh.domain.form.GoodForm;
import com.myh.service.ReportsService;

@Controller
public class TestController {

	@Autowired
	private ReportsService reportsService;
	
	@RequestMapping("/test")
	public ModelAndView test() {
		ModelAndView modelAndView = new ModelAndView("test");
		modelAndView.addObject("hello", "请不要乱码！");
		return modelAndView;
	}
	
	@RequestMapping("/success")
	public @ResponseBody GoodForm success(@ModelAttribute GoodForm goodForm) {
		return goodForm;
	}
	
	
	@RequestMapping("/allreports")
	public @ResponseBody List<StockReports> analyseAllReports(){
		return reportsService.findAllStockReports();
	}
	
	@RequestMapping("/monthreports")
	public @ResponseBody List<StockReports> analyseMonthReports(){
		return reportsService.findStockReportsByMonth("2016-10");
	}
	
	@RequestMapping("/seasonreports")
	public @ResponseBody List<StockReports> analyseSeasonReports(){
		return reportsService.findStockReportsBySeason("2016-08-01","2016-08-10");
	}
	
	@RequestMapping("/yearreports")
	public @ResponseBody List<StockReports> analyseYearReports(){
		return reportsService.findStockReportsByYear("2016");
	}
	
	@RequestMapping("/allDetail")
	public @ResponseBody List<StockDetail> analyseAllDetail(){
		return reportsService.findAllStockDetail();
	}
}
