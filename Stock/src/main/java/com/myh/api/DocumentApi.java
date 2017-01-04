package com.myh.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.myh.domain.CustomResponse;
import com.myh.domain.Good;
import com.myh.domain.InStock;
import com.myh.domain.InStockItem;
import com.myh.domain.Inventory;
import com.myh.domain.JGPage;
import com.myh.domain.OutStock;
import com.myh.domain.OutStockItem;
import com.myh.domain.User;
import com.myh.service.GoodService;
import com.myh.service.InStockService;
import com.myh.service.InventoryService;
import com.myh.service.OutStockService;
import com.myh.service.ReportsService;
import com.myh.service.UserService;

@RestController
@RequestMapping("/api/document")
public class DocumentApi {

	@Autowired
	private ReportsService reportsService;
	@Autowired
	private InStockService inStockService;
	@Autowired
	private OutStockService outStockService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private GoodService goodService;
	
	@RequestMapping("/all/{page}")
	public ApiResult<JGPage<Stock>> getDocumentList(@PathVariable("page") int page){
		ApiResult<JGPage<Stock>> apiResult = new ApiResult<JGPage<Stock>>();
        JGPage<Stock> jgPage = null;
        try{
        	jgPage = reportsService.getAllDocument(page);
        }catch(Exception e){
        	apiResult.setCode(500);
        	apiResult.setMsg(e.getMessage());
        	apiResult.setObject(null);
        	return apiResult;
        }
    	apiResult.setCode(200);
    	apiResult.setMsg("success");
    	apiResult.setObject(jgPage);
    	return apiResult;
	}
	
	@RequestMapping("/instock/{page}")
	public ApiResult<JGPage<Stock>> getInstockList(@PathVariable("page") int page){
		ApiResult<JGPage<Stock>> apiResult = new ApiResult<JGPage<Stock>>();
        JGPage<Stock> jgPage = null;
        try{
        	jgPage = reportsService.findAllInStock(page);
        }catch(Exception e){
        	apiResult.setCode(500);
        	apiResult.setMsg(e.getMessage());
        	apiResult.setObject(null);
        	return apiResult;
        }
    	apiResult.setCode(200);
    	apiResult.setMsg("success");
    	apiResult.setObject(jgPage);
    	return apiResult;
	}
	
	@RequestMapping("/outstock/{page}")
	public ApiResult<JGPage<Stock>> getOutstockList(@PathVariable("page") int page){
		ApiResult<JGPage<Stock>> apiResult = new ApiResult<JGPage<Stock>>();
        JGPage<Stock> jgPage = null;
        try{
        	jgPage = reportsService.findAllOutStock(page);
        }catch(Exception e){
        	apiResult.setCode(500);
        	apiResult.setMsg(e.getMessage());
        	apiResult.setObject(null);
        	return apiResult;
        }
    	apiResult.setCode(200);
    	apiResult.setMsg("success");
    	apiResult.setObject(jgPage);
    	return apiResult;
	}
	
	@RequestMapping("/inventory/{page}")
	public ApiResult<JGPage<Inventory>> getInventoryList(@PathVariable("page") int page){
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 15, sort);
		ApiResult<JGPage<Inventory>> apiResult = new ApiResult<JGPage<Inventory>>();
        JGPage<Inventory> jgPage = null;
        try{
        	jgPage = inventoryService.finaAll(null, pageable);
        }catch(Exception e){
        	apiResult.setCode(500);
        	apiResult.setMsg(e.getMessage());
        	apiResult.setObject(null);
        	return apiResult;
        }
    	apiResult.setCode(200);
    	apiResult.setMsg("success");
    	apiResult.setObject(jgPage);
    	return apiResult;
	}
	
	@RequestMapping("/findByCondition/{type}/{key}/{date}/{page}")
	public ApiResult<JGPage<Stock>> findByCondition(
			@PathVariable("type") String type,
			@PathVariable("key") String key,
			@PathVariable("date") String date,
			@PathVariable("page") int page){
		ApiResult<JGPage<Stock>> apiResult = new ApiResult<JGPage<Stock>>();
        JGPage<Stock> jgPage = null;
        try{
        	//jgPage = reportsService.findDocumentByIdOrType(key, page);
        	jgPage = reportsService.findDocumentByCondition(type, key, date, page);
        }catch(Exception e){
        	apiResult.setCode(500);
        	apiResult.setMsg(e.getMessage());
        	apiResult.setObject(null);
        	return apiResult;
        }
    	apiResult.setCode(200);
    	apiResult.setMsg("success");
    	apiResult.setObject(jgPage);
    	return apiResult;
	}
	
	@RequestMapping("/findOne/{stockId}")
	public ApiResult<Object> findOne(@PathVariable("stockId") String stockId){
		ApiResult<Object> apiResult = new ApiResult<Object>();
        List list = null;
		try{
        	if(stockId.indexOf("RK") != -1){
        		List<InStockItem> inStockItems = inStockService.findOneInItems(stockId);
        		list = inStockItems;
        	}else if(stockId.indexOf("CK") != -1){
        		List<OutStockItem> outStockItems = outStockService.findOneOutItems(stockId);
        		list = outStockItems;
        	}
        }catch(Exception e){
        	apiResult.setCode(500);
        	apiResult.setMsg(e.getMessage());
        	apiResult.setObject(null);
        	return apiResult;
        }
    	apiResult.setCode(200);
    	apiResult.setMsg("success");
    	apiResult.setObject(list);
    	return apiResult;
	}

	@RequestMapping(value="/addDocument/{type}", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public ApiResult<Object> addDocument(
			@PathVariable("type") String type, @RequestBody DocumentBody documentBody){
		ApiResult<Object> apiResult = new ApiResult<Object>();
		int worth = 0;
		if(documentBody != null){
			if(type.equals("instock")){
				InStock inStock = new InStock();
				inStock.setId("RK"+System.currentTimeMillis());
				inStock.setDate(new Date(System.currentTimeMillis()));
				inStock.setDocuments(documentBody.getDocument());
				inStock.setRemark(documentBody.getRemark());
				inStock.setType(documentBody.getType());
				try {
					inStock.setUser(userService.findOne(documentBody.getUser()));
					inStockService.save(inStock);
					Good[] goods = documentBody.getGoods();
					int[] nums = documentBody.getNums();
 					for(int i = 0; i < goods.length ; i++){
						InStockItem inStockItem = new InStockItem();
						Good good = goods[i];
						good.setCur_stock(good.getCur_stock()+nums[i]);
						goodService.save(good);
						inStockItem.setGood(good);
						inStockItem.setInStock(inStock);
						inStockItem.setNum(nums[i]);
						int itemWorth = good.getIn_price()*nums[i];
						worth += itemWorth;
						inStockItem.setWorth(itemWorth);
						inStockService.saveItems(inStockItem);
					}
				} catch (Exception e) {
					apiResult.setCode(500);
		        	apiResult.setMsg(e.getMessage());
		        	apiResult.setObject(null);
		        	return apiResult;
				}
				apiResult.setCode(200);
	        	apiResult.setMsg("success");
	        	Stock stock = new Stock();
	        	stock.setId(inStock.getId());
	        	stock.setDate(inStock.getDate().toString());
	        	stock.setType(inStock.getType());
	        	stock.setDocuments(inStock.getDocuments());
	        	stock.setUser(inStock.getUser().getName());
	        	stock.setWorth(worth+"");
	        	apiResult.setObject(stock);
				return apiResult;
			}else{
				OutStock outStock = new OutStock();
				outStock.setId("CK"+System.currentTimeMillis());
				outStock.setDate(new Date(System.currentTimeMillis()));
				outStock.setDocuments(documentBody.getDocument());
				outStock.setRemark(documentBody.getRemark());
				outStock.setType(documentBody.getType());
				try {
					outStock.setUser(userService.findOne(documentBody.getUser()));
					outStockService.save(outStock);
					Good[] goods = documentBody.getGoods();
					int[] nums = documentBody.getNums();
					for(int i = 0; i < goods.length ; i++){
						OutStockItem outStockItem = new OutStockItem();
						Good good = goods[i];
						good.setCur_stock(good.getCur_stock()-nums[i]);
						goodService.save(good);
						outStockItem.setGood(good);
						outStockItem.setOutStock(outStock);
						outStockItem.setNum(nums[i]);
						int itemWorth = good.getOut_price()*nums[i];
						worth += itemWorth;
						outStockItem.setWorth(itemWorth);
						outStockService.saveItems(outStockItem);
					}
				} catch (Exception e) {
					apiResult.setCode(500);
		        	apiResult.setMsg(e.getMessage());
		        	apiResult.setObject(null);
		        	return apiResult;
				}
				apiResult.setCode(200);
	        	apiResult.setMsg("success");
	        	Stock stock = new Stock();
	        	stock.setId(outStock.getId());
	        	stock.setDate(outStock.getDate().toString());
	        	stock.setType(outStock.getType());
	        	stock.setDocuments(outStock.getDocuments());
	        	stock.setUser(outStock.getUser().getName());
	        	stock.setWorth(worth+"");
	        	apiResult.setObject(stock);
				return apiResult;
			}
		}
		apiResult.setCode(500);
    	apiResult.setMsg("RequestBody is null");
    	apiResult.setObject(null);
		return apiResult;
	}
	
	@RequestMapping(value="/uploadDocu/", method=RequestMethod.POST)
	public ApiResult<String> uploadDocu(MultipartHttpServletRequest request){
		ApiResult<String> apiResult = new ApiResult<>();
		try {
            Iterator<String> itr = request.getFileNames();
            String output = "";
            while (itr.hasNext()) {
                String uploadedFile = itr.next();
                MultipartFile file = request.getFile(uploadedFile);
                //String mimeType = file.getContentType();
                //String filename = file.getOriginalFilename();
                //byte[] bytes = file.getBytes();
                byte[] bytes = file.getBytes();
                String dir = "F:/MyEclipseProject/Stock/pic/";
                //String dir = "C:/Stock/pic/";
                String fileName = "DJ_"+System.currentTimeMillis()+".png";
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(dir+fileName)));
                stream.write(bytes);
                stream.close();
                
                output += fileName+"#";
            }
            apiResult.setCode(200);
            apiResult.setMsg("success");
            apiResult.setObject(output);
            return apiResult;
        }
        catch (Exception e) {
            apiResult.setCode(200);
            apiResult.setMsg("success");
            apiResult.setObject(e.getMessage());
            return apiResult;
        }
	}

}
