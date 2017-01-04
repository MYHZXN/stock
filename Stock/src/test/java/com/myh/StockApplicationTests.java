package com.myh;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.myh.domain.Good;
import com.myh.domain.Unit;
import com.myh.repository.UnitRepository;
import com.myh.service.GoodService;
import com.myh.service.ReportsService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(StockApplication.class)
@WebAppConfiguration
public class StockApplicationTests {

//	@Autowired
//	private GoodService goodService;
//	@Autowired
//	private UnitRepository unitRepository;
	
	@Autowired
	private ReportsService reportsService;
	
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testReports(){
	}

	
//	@Test
//	public void testGood() throws Exception{
//		Unit unit = unitRepository.getOne(1);
//		Good good1 = new Good("GD"+System.currentTimeMillis(), "978020137972", "test1", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good2 = new Good("GD"+System.currentTimeMillis(), "978020137973", "test2", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good3 = new Good("GD"+System.currentTimeMillis(), "978020137974", "test3", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good4 = new Good("GD"+System.currentTimeMillis(), "978020137975", "test4", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good5 = new Good("GD"+System.currentTimeMillis(), "978020137976", "test5", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good6 = new Good("GD"+System.currentTimeMillis(), "978020137977", "test6", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good7 = new Good("GD"+System.currentTimeMillis(), "978020137978", "test7", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good8 = new Good("GD"+System.currentTimeMillis(), "978020137979", "test8", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good9 = new Good("GD"+System.currentTimeMillis(), "978020137980", "test9", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good10 = new Good("GD"+System.currentTimeMillis(), "978020137981", "test10", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Good good11 = new Good("GD"+System.currentTimeMillis(), "978020137972", "test11", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good12 = new Good("GD"+System.currentTimeMillis(), "978020137973", "test12", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good13 = new Good("GD"+System.currentTimeMillis(), "978020137974", "test13", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good14 = new Good("GD"+System.currentTimeMillis(), "978020137975", "test14", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good15 = new Good("GD"+System.currentTimeMillis(), "978020137976", "test15", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good16 = new Good("GD"+System.currentTimeMillis(), "978020137977", "test16", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good17 = new Good("GD"+System.currentTimeMillis(), "978020137978", "test17", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good18 = new Good("GD"+System.currentTimeMillis(), "978020137979", "test18", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good19 = new Good("GD"+System.currentTimeMillis(), "978020137980", "test19", 
//				unit, 10000, 15000, 0, 0, 9999);
//		Thread.sleep(1000);
//		Good good20 = new Good("GD"+System.currentTimeMillis(), "978020137981", "test20", 
//				unit, 10000, 15000, 0, 0, 9999);
//		
//		goodService.save(good11);
//		goodService.save(good12);
//		goodService.save(good13);
//		goodService.save(good14);
//		goodService.save(good15);
//		goodService.save(good16);
//		goodService.save(good17);
//		goodService.save(good18);
//		goodService.save(good19);
//		goodService.save(good20);
//		
//		
//	}

}
