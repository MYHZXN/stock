package com.myh.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myh.domain.User;
import com.myh.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserApi {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ApiResult<User> login(@RequestBody User user){
		ApiResult<User> apiResult = new ApiResult<>();
		User u = null;
		try{
			u = userService.findOne(user);
		}catch(Exception e){
			apiResult.setCode(500);
			apiResult.setMsg(e.getMessage());
			apiResult.setObject(null);
		}
		if(u != null){
			apiResult.setCode(200);
			apiResult.setMsg("success");
			apiResult.setObject(u);
		}else{
			apiResult.setCode(200);
			apiResult.setMsg("用户名或者密码错误");
			apiResult.setObject(null);
		}
		return apiResult;
	}
	

}
