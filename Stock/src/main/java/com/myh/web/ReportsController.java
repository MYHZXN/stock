package com.myh.web;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myh.domain.CustomResponse;
import com.myh.domain.StockReports;
import com.myh.domain.form.ReportsCondition;
import com.myh.service.ReportsService;

@Controller
@RequestMapping("/reports")
public class ReportsController {

	@Autowired
	private ReportsService reportsService;
	
	@RequestMapping("/showReports")
	public String showReports(){
		return "reports";
	}
	
	@RequestMapping("/analyseReports")
	public @ResponseBody CustomResponse analyseReports(@ModelAttribute ReportsCondition reportsCondition){
		if(reportsCondition == null){
			return new CustomResponse("500", "条件为空！");
		}else{
			List<StockReports> reports = null;
			String dateCondition = reportsCondition.getDateCondition();
			if(dateCondition.equals("lastmonth")){
				Calendar calendar=Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				month = month==0?1:month;
				reports = reportsService.findStockReportsByMonth(year+"-"+(month>=10?month:"0"+month));
			}else if(dateCondition.equals("thismonth")){
				Calendar calendar=Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				int month=calendar.get(Calendar.MONTH)+1;
				reports = reportsService.findStockReportsByMonth(year+"-"+(month>=10?month:"0"+month));
			}else if(dateCondition.equals("season")){
				if(!StringUtils.isEmpty(reportsCondition.getSeasonYear())
						&&!StringUtils.isEmpty(reportsCondition.getSeasonWhich())){
					String startdate = "";
					String enddate = "";
					if(reportsCondition.getSeasonWhich().equals("1")){
						startdate = reportsCondition.getSeasonYear()+"-01-1";
						enddate = reportsCondition.getSeasonYear()+"-03-31";
					}else if(reportsCondition.getSeasonWhich().equals("2")){
						startdate = reportsCondition.getSeasonYear()+"-04-1";
						enddate = reportsCondition.getSeasonYear()+"-06-30";
					}else if(reportsCondition.getSeasonWhich().equals("3")){
						startdate = reportsCondition.getSeasonYear()+"-07-1";
						enddate = reportsCondition.getSeasonYear()+"-09-30";
					}else if(reportsCondition.getSeasonWhich().equals("4")){
						startdate = reportsCondition.getSeasonYear()+"-10-1";
						enddate = reportsCondition.getSeasonYear()+"-12-31";
					}
					reports = reportsService.findStockReportsBySeason(startdate, enddate);
				}
			}else if(dateCondition.equals("year")){
				if(!StringUtils.isEmpty(reportsCondition.getYearWhich())){
					reports = reportsService.findStockReportsByYear(reportsCondition.getYearWhich());
				}
			}else if(dateCondition.equals("between")){
				if(!StringUtils.isEmpty(reportsCondition.getStartdate())
						&&!StringUtils.isEmpty(reportsCondition.getEnddate())){
					reports = reportsService.findStockReportsBySeason(reportsCondition.getStartdate(), reportsCondition.getEnddate());
				}
			}
			CustomResponse response = new CustomResponse("200", "");
			response.setObj(reports);
			return response;
		}
	}
	
	@RequestMapping("/showDetails")
	public String showDetails(){
		return "details";
	}
	
	@RequestMapping("/analyseDetails")
	public @ResponseBody CustomResponse analyseDetails(){
		CustomResponse response = new CustomResponse("200", "");
		response.setObj(reportsService.findAllStockDetail());
		return response;
	}
}
