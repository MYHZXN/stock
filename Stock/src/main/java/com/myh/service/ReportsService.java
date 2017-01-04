package com.myh.service;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.myh.api.Stock;
import com.myh.api.StockData;
import com.myh.domain.JGPage;
import com.myh.domain.StockDetail;
import com.myh.domain.StockReports;

@Service
public class ReportsService {
	
	private static final Log log = LogFactory.getLog(ReportsService.class);
	
	@PersistenceContext
	private EntityManager entityManager; 

	
    public List<StockReports> findAllStockReports() {  
        Query query = entityManager.createNativeQuery(
        		"SELECT g.id,g.name,"
        		+ "count(initem.good_id),sum(initem.num),sum(initem.worth),"
        		+ "count(outitem.good_id),sum(outitem.num),sum(outitem.worth),g.cur_stock "
        		+ "from (good g left join in_stock_item initem on g.id=initem.good_id) "
        		+ "left join out_stock_item outitem on g.id=outitem.good_id group by g.id");
  
        List<StockReports> reports = new ArrayList<>();
        List result = query.getResultList();
        if(result != null){
        	Iterator iterator = result.iterator();
        	while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();
				StockReports stockReports = new StockReports();
				stockReports.setId(row[0].toString());
				stockReports.setName(row[1].toString());
				stockReports.setIncout((BigInteger)row[2]);
				stockReports.setInnum(row[3]!=null?(new BigDecimal(row[3].toString())) : new BigDecimal(0));
				stockReports.setInworth(row[4]!=null?(new BigDecimal(row[4].toString())):new BigDecimal(0));
				stockReports.setOutcout((BigInteger)row[5]);
				stockReports.setOutnum(row[6]!=null?(new BigDecimal(row[6].toString())):new BigDecimal(0));
				stockReports.setOutworth(row[7]!=null?(new BigDecimal(row[7].toString())):new BigDecimal(0));
				stockReports.setCurstock((int)row[8]);
				reports.add(stockReports);
        	}
        }else{
        	return null;
        }
        return reports;  
  
    }  
    
    
    public List<StockReports> findStockReportsByMonth(String monthCondition){
        Query query = entityManager.createNativeQuery(
        		"SELECT g.id,g.name,"
        		+ "count(initem.good_id),sum(initem.num),sum(initem.worth),"
        		+ "count(outitem.good_id),sum(outitem.num),sum(outitem.worth),g.cur_stock "
        		+ "from (good g left join (select * from in_stock_item where instock_id in (select id from in_stock where date_format(date,'%Y-%m')='"+monthCondition+"')) initem on g.id=initem.good_id) "
        		+ "left join (select * from out_stock_item where outstock_id in (select id from out_stock where date_format(date,'%Y-%m')='"+monthCondition+"')) outitem on g.id=outitem.good_id "
        		+ "group by g.id");
  
        List<StockReports> reports = new ArrayList<>();
        List result = query.getResultList();
        if(result != null){
        	Iterator iterator = result.iterator();
        	while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();
				StockReports stockReports = new StockReports();
				stockReports.setId(row[0].toString());
				stockReports.setName(row[1].toString());
				stockReports.setIncout((BigInteger)row[2]);
				stockReports.setInnum(row[3]!=null?(new BigDecimal(row[3].toString())) : new BigDecimal(0));
				stockReports.setInworth(row[4]!=null?(new BigDecimal(row[4].toString())):new BigDecimal(0));
				stockReports.setOutcout((BigInteger)row[5]);
				stockReports.setOutnum(row[6]!=null?(new BigDecimal(row[6].toString())):new BigDecimal(0));
				stockReports.setOutworth(row[7]!=null?(new BigDecimal(row[7].toString())):new BigDecimal(0));
				stockReports.setCurstock((int)row[8]);
				reports.add(stockReports);
        	}
        }else{
        	return null;
        }
        return reports;  
    }
    
    public List<StockReports> findStockReportsBySeason(String startCondition,String endCondition){
        Query query = entityManager.createNativeQuery(
        		"SELECT g.id,g.name,"
        		+ "count(initem.good_id),sum(initem.num),sum(initem.worth),"
        		+ "count(outitem.good_id),sum(outitem.num),sum(outitem.worth),g.cur_stock "
        		+ "from (good g left join (select * from in_stock_item where instock_id in (select id from in_stock where date between '"+startCondition+"' and '"+endCondition+"')) initem on g.id=initem.good_id) "
        		+ "left join (select * from out_stock_item where outstock_id in (select id from out_stock where date between '"+startCondition+"' and '"+endCondition+"')) outitem on g.id=outitem.good_id "
        		+ "group by g.id");
  
        List<StockReports> reports = new ArrayList<>();
        List result = query.getResultList();
        if(result != null){
        	Iterator iterator = result.iterator();
        	while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();
				StockReports stockReports = new StockReports();
				stockReports.setId(row[0].toString());
				stockReports.setName(row[1].toString());
				stockReports.setIncout((BigInteger)row[2]);
				stockReports.setInnum(row[3]!=null?(new BigDecimal(row[3].toString())) : new BigDecimal(0));
				stockReports.setInworth(row[4]!=null?(new BigDecimal(row[4].toString())):new BigDecimal(0));
				stockReports.setOutcout((BigInteger)row[5]);
				stockReports.setOutnum(row[6]!=null?(new BigDecimal(row[6].toString())):new BigDecimal(0));
				stockReports.setOutworth(row[7]!=null?(new BigDecimal(row[7].toString())):new BigDecimal(0));
				stockReports.setCurstock((int)row[8]);
				reports.add(stockReports);
        	}
        }else{
        	return null;
        }
        return reports;
    }
    
    public List<StockReports> findStockReportsByYear(String yearCondition){
        Query query = entityManager.createNativeQuery(
        		"SELECT g.id,g.name,"
        		+ "count(initem.good_id),sum(initem.num),sum(initem.worth),"
        		+ "count(outitem.good_id),sum(outitem.num),sum(outitem.worth),g.cur_stock "
        		+ "from (good g left join (select * from in_stock_item where instock_id in (select id from in_stock where date_format(date,'%Y')='"+yearCondition+"')) initem on g.id=initem.good_id) "
        		+ "left join (select * from out_stock_item where outstock_id in (select id from out_stock where date_format(date,'%Y')='"+yearCondition+"')) outitem on g.id=outitem.good_id "
        		+ "group by g.id");
  
        List<StockReports> reports = new ArrayList<>();
        List result = query.getResultList();
        if(result != null){
        	Iterator iterator = result.iterator();
        	while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();
				StockReports stockReports = new StockReports();
				stockReports.setId(row[0].toString());
				stockReports.setName(row[1].toString());
				stockReports.setIncout((BigInteger)row[2]);
				stockReports.setInnum(row[3]!=null?(new BigDecimal(row[3].toString())) : new BigDecimal(0));
				stockReports.setInworth(row[4]!=null?(new BigDecimal(row[4].toString())):new BigDecimal(0));
				stockReports.setOutcout((BigInteger)row[5]);
				stockReports.setOutnum(row[6]!=null?(new BigDecimal(row[6].toString())):new BigDecimal(0));
				stockReports.setOutworth(row[7]!=null?(new BigDecimal(row[7].toString())):new BigDecimal(0));
				stockReports.setCurstock((int)row[8]);
				reports.add(stockReports);
        	}
        }else{
        	return null;
        }
        return reports;  
    }

    public List<StockDetail> findAllStockDetail(){
        Query query = entityManager.createNativeQuery(
        		"select instock.id as 'id',instock.type as 'type',instock.date as 'date',initem.good_id as 'goodId',g.name as 'goodName',initem.num as 'num',initem.worth as 'worth' "
        		+ "from in_stock instock,in_stock_item initem,good g where instock.id=initem.instock_id and g.id=initem.good_id "
        		+ "union "
        		+ "select outstock.id as 'id',outstock.type as 'type',outstock.date as 'date',outitem.good_id as 'goodId',g.name as 'goodName',outitem.num as 'num',outitem.worth as 'worth' "
        		+ "from out_stock outstock,out_stock_item outitem,good g where outstock.id=outitem.outstock_id and g.id=outitem.good_id order by date asc");
  
        List<StockDetail> details = new ArrayList<>();
        List result = query.getResultList();
        if(result != null){
        	Iterator iterator = result.iterator();
        	while (iterator.hasNext()) {
				Object[] row = (Object[]) iterator.next();
				StockDetail detail = new StockDetail();
				detail.setId(row[0].toString());
				detail.setType(row[1].toString());
				detail.setDate(row[2].toString().substring(0, 10));
				detail.setGoodId(row[3].toString());
				detail.setGoodName(row[4].toString());
				detail.setNum((int)row[5]);
				detail.setWorth((int)row[6]);
				details.add(detail);
        	}
        }else{
        	return null;
        }
        return details;
    }

    public String calStockWorth(){
    	Query query = entityManager.createNativeQuery(
    			"SELECT sum(cur_stock*in_price) as 'stockworth' from good");
    	return query.getResultList().get(0).toString();
    }
    
    public JGPage<Stock> getAllDocument(int page){
    	int rows = 15;
    	String limit = "limit "+(page-1)*rows+","+rows;
		Query query = entityManager.createNativeQuery(
				"select instock.id as 'id',instock.type as 'type',instock.date as 'date',sum(initem.worth) as 'worth'"
				+ ",u.name as 'user',instock.documents as 'document' "
				+ "from in_stock instock,in_stock_item initem,user u "
				+ "where instock.id=initem.instock_id and instock.user_Id=u.id group by instock.id "
				+ "union "
				+ "select outstock.id as 'id',outstock.type as 'type',outstock.date as 'date',sum(outitem.worth) as 'worth'"
				+ ",u.name as 'user',outstock.documents as 'document' "
				+ "from out_stock outstock,out_stock_item outitem,user u "
				+ "where outstock.id=outitem.outstock_id and outstock.user_Id=u.id group by outstock.id "
				+ "order by date asc "
				+ limit);
		
		List<Stock> stocks = new ArrayList<>();
		List result = query.getResultList();
		if(result != null){
			Iterator iterator = result.iterator();
        	while (iterator.hasNext()) {
        		Object[] row = (Object[]) iterator.next();
        		Stock stock = new Stock();
        		stock.setId(row[0].toString());
        		stock.setType(row[1].toString());
        		stock.setDate(row[2].toString().substring(0,10));
        		stock.setWorth(row[3].toString());
        		stock.setUser(row[4].toString());
        		stock.setDocuments(row[5].toString());
        		stocks.add(stock);
        	}
		}else{
			return null;
		}
		Query coutQuery = entityManager.createNativeQuery(
				"select instock.id as 'id'"
			    + "from in_stock instock,in_stock_item initem where instock.id=initem.instock_id group by instock.id "
			    + "union "
				+ "select outstock.id as 'id'"
				+ "from out_stock outstock,out_stock_item outitem where outstock.id=outitem.outstock_id group by outstock.id ");
		Long records = (long) coutQuery.getResultList().size();
		int total;
		if(records%rows==0){
			total = (int) (records/rows);
		}else{
			total = (int) (records/rows + 1);
		}
    	return new JGPage<Stock>(records, page, total, stocks);
    }
    
    public JGPage<Stock> findAllInStock(int page){
    	int rows = 15;
    	String limit = "limit "+(page-1)*rows+","+rows;
		Query query = entityManager.createNativeQuery(
				"select instock.id as 'id',instock.type as 'type',instock.date as 'date',sum(initem.worth) as 'worth'"
				+ ",u.name as 'user',instock.documents as 'document' "
				+ "from in_stock instock,in_stock_item initem,user u "
				+ "where instock.id=initem.instock_id and instock.user_Id=u.id group by instock.id "
				+ limit);
		
		List<Stock> stocks = new ArrayList<>();
		List result = query.getResultList();
		if(result != null){
			Iterator iterator = result.iterator();
        	while (iterator.hasNext()) {
        		Object[] row = (Object[]) iterator.next();
        		Stock stock = new Stock();
        		stock.setId(row[0].toString());
        		stock.setType(row[1].toString());
        		stock.setDate(row[2].toString().substring(0,10));
        		stock.setWorth(row[3].toString());
        		stock.setUser(row[4].toString());
        		stock.setDocuments(row[5].toString());
        		stocks.add(stock);
        	}
		}else{
			return null;
		}
		Query coutQuery = entityManager.createNativeQuery(
				"select instock.id as 'id'"
			    + "from in_stock instock,in_stock_item initem where instock.id=initem.instock_id group by instock.id ");
		Long records = (long) coutQuery.getResultList().size();
		int total;
		if(records%rows==0){
			total = (int) (records/rows);
		}else{
			total = (int) (records/rows + 1);
		}
    	return new JGPage<Stock>(records, page, total, stocks);
    }
    
    public JGPage<Stock> findAllOutStock(int page){
    	int rows = 15;
    	String limit = "limit "+(page-1)*rows+","+rows;
		Query query = entityManager.createNativeQuery(
				"select outstock.id as 'id',outstock.type as 'type',outstock.date as 'date',sum(outitem.worth) as 'worth'"
				+ ",u.name as 'user',outstock.documents as 'document' "
				+ "from out_stock outstock,out_stock_item outitem,user u "
				+ "where outstock.id=outitem.outstock_id and outstock.user_Id=u.id group by outstock.id "
				+ limit);
		
		List<Stock> stocks = new ArrayList<>();
		List result = query.getResultList();
		if(result != null){
			Iterator iterator = result.iterator();
        	while (iterator.hasNext()) {
        		Object[] row = (Object[]) iterator.next();
        		Stock stock = new Stock();
        		stock.setId(row[0].toString());
        		stock.setType(row[1].toString());
        		stock.setDate(row[2].toString().substring(0,10));
        		stock.setWorth(row[3].toString());
        		stock.setUser(row[4].toString());
        		stock.setDocuments(row[5].toString());
        		stocks.add(stock);
        	}
		}else{
			return null;
		}
		Query coutQuery = entityManager.createNativeQuery(
				"select outstock.id as 'id'"
				+ "from out_stock outstock,out_stock_item outitem where outstock.id=outitem.outstock_id group by outstock.id ");
		Long records = (long) coutQuery.getResultList().size();
		int total;
		if(records%rows==0){
			total = (int) (records/rows);
		}else{
			total = (int) (records/rows + 1);
		}
    	return new JGPage<Stock>(records, page, total, stocks);
    }
    
    public JGPage<Stock> findDocumentByCondition(String type, String key, String date, int page){
    	int rows = 15;
    	String limit = "limit "+(page-1)*rows+","+rows;
		String typeCondition = "";
    	String keyCondtion1 = "";
    	String keyCondtion2 = "";
    	String dateCondition = "";
    	String sql = "";
    	if(!key.equals("null")){
    		keyCondtion1 = "and (instock.id like '%"+key+"%' or type like '%"+key+"%') ";
    		keyCondtion2 = "and (outstock.id like '%"+key+"%' or type like '%"+key+"%') ";
    	}
    	if(!date.equals("null")){
    		dateCondition = "and date = '"+date+"' ";
    	}
    	if(!StringUtils.isEmpty(type)){
			switch(type){
				case "all":
					sql = "select instock.id as 'id',instock.type as 'type',instock.date as 'date',sum(initem.worth) as 'worth' "
							+ ",u.name as 'user',instock.documents as 'document' "
							+ "from in_stock instock,in_stock_item initem,user u where instock.id=initem.instock_id and instock.user_Id=u.id "
							+ keyCondtion1 + dateCondition
							+ "group by instock.id "
							+ "union "
							+ "select outstock.id as 'id',outstock.type as 'type',outstock.date as 'date',sum(outitem.worth) as 'worth' "
							+ ",u.name as 'user',outstock.documents as 'document' "
							+ "from out_stock outstock,out_stock_item outitem,user u where outstock.id=outitem.outstock_id and outstock.user_Id=u.id "
							+ keyCondtion2 + dateCondition
							+ "group by outstock.id ";
					break;
				case "instock":
					sql = "select instock.id as 'id',instock.type as 'type',instock.date as 'date',sum(initem.worth) as 'worth' "
							+ ",u.name as 'user',instock.documents as 'document' "
							+ "from in_stock instock,in_stock_item initem,user u where instock.id=initem.instock_id and instock.user_Id=u.id "
							+ keyCondtion1 + dateCondition
							+ "group by instock.id ";
					break;
				case "outstock":
					sql = "select outstock.id as 'id',outstock.type as 'type',outstock.date as 'date',sum(outitem.worth) as 'worth' "
							+ ",u.name as 'user',outstock.documents as 'document' "
							+ "from out_stock outstock,out_stock_item outitem,user u where outstock.id=outitem.outstock_id and outstock.user_Id=u.id "
							+ keyCondtion2 + dateCondition
							+ "group by outstock.id ";
					break;
			}
		}
    	Query query = entityManager.createNativeQuery(
				sql
				+ "order by date asc "
				+ limit);
		
		List<Stock> stocks = new ArrayList<>();
		List result = query.getResultList();
		if(result != null){
			Iterator iterator = result.iterator();
        	while (iterator.hasNext()) {
        		Object[] row = (Object[]) iterator.next();
        		Stock stock = new Stock();
        		stock.setId(row[0].toString());
        		stock.setType(row[1].toString());
        		stock.setDate(row[2].toString().substring(0,10));
        		stock.setWorth(row[3].toString());
        		stock.setUser(row[4].toString());
        		stock.setDocuments(row[5].toString());
        		stocks.add(stock);
        	}
		}else{
			return null;
		}
		Query coutQuery = entityManager.createNativeQuery(sql);
		Long records = (long) coutQuery.getResultList().size();
		int total;
		if(records%rows==0){
			total = (int) (records/rows);
		}else{
			total = (int) (records/rows + 1);
		}
    	return new JGPage<Stock>(records, page, total, stocks);
    }
    
    public StockData countData(String today,String month){
		Query tiwQuery = entityManager.createNativeQuery(
				"select sum(worth) as todayInWorth from in_stock_item where instock_id in (select id from in_stock where date='"+today+"')");
		Query towQuery = entityManager.createNativeQuery(
				"select sum(worth) as todayOutWorth from out_stock_item where outstock_id in (select id from out_stock where date='"+today+"')");
		Query miwQuery = entityManager.createNativeQuery(
				"select sum(worth) as monthInWorth from in_stock_item where instock_id in (select id from in_stock where date_format(date,'%Y-%m')='"+month+"')");
		Query mowQuery = entityManager.createNativeQuery(
				"select sum(worth) as monthOutWorth from out_stock_item where outstock_id in (select id from out_stock where date_format(date,'%Y-%m')='"+month+"')");
		Query ticQuery = entityManager.createNativeQuery(
				"select count(good_id) as todayInCount from in_stock_item where instock_id in (select id from in_stock where date='"+today+"')");
		Query tocQuery = entityManager.createNativeQuery(
				"select count(good_id) as todayOutCount from out_stock_item where outstock_id in (select id from out_stock where date='"+today+"')");
		
		StockData stockData = new StockData();
		
		stockData.setTodayInWorth(tiwQuery.getSingleResult() == null ? "0.00" : tiwQuery.getSingleResult().toString());
		stockData.setTodayOutWorth(towQuery.getSingleResult() == null ? "0.00" : towQuery.getSingleResult().toString());
		stockData.setMonthInWorth(miwQuery.getSingleResult() == null ? "0.00" : miwQuery.getSingleResult().toString());
		stockData.setMonthOutWorth(mowQuery.getSingleResult() == null ? "0.00" : mowQuery.getSingleResult().toString());
		stockData.setTodayInCount(ticQuery.getSingleResult() == null ? "0" : ticQuery.getSingleResult().toString());
		stockData.setTodayOutCount(tocQuery.getSingleResult() == null ? "0" : tocQuery.getSingleResult().toString());
		
		return stockData;
    }
    
    
}
