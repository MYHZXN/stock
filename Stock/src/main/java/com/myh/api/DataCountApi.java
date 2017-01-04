package com.myh.api;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myh.service.GoodService;
import com.myh.service.ReportsService;

@RestController
@RequestMapping("/api/datacount")
public class DataCountApi {

	@Autowired
	private GoodService goodService;
	@Autowired
	private ReportsService reportsService;
	
	@RequestMapping("/stockdata")
	public ApiResult<StockData> getStockData(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH);//获取月份
        int day=cal.get(Calendar.DATE);//获取日
		String date1 = year+"-"+month+"-"+day;
		String date2 = year+"-"+month;
		StockData stockData = reportsService.countData(date1, date2);
		stockData.setGoodCount(goodService.countAll());
		stockData.setGoodLess(goodService.countByCurStock());
		stockData.setStockWorth(reportsService.calStockWorth());
		
		ApiResult<StockData> apiResult = new ApiResult<>();
		apiResult.setCode(200);
		apiResult.setMsg("success");
		apiResult.setObject(stockData);
		return apiResult;
	}
	
}
