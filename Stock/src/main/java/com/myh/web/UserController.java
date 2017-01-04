package com.myh.web;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myh.domain.CustomResponse;
import com.myh.domain.JGPage;
import com.myh.domain.User;
import com.myh.domain.form.UserForm;
import com.myh.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/showUserList")
	public String showUserList(){
		return "user";
	}
	
	@RequestMapping("/findAll")
	public @ResponseBody JGPage<User> findAll(
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort); 
		return userService.finaAll(null, pageable);
	}
	
	@RequestMapping("/findByCondition")
	public @ResponseBody JGPage<User> findByCondition(@ModelAttribute UserForm userForm,
			@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception{
		User user = new User();
		BeanUtils.copyProperties(userForm, user);
//		if(StringUtils.isEmpty(user.getId())){
//			user.setId(System.currentTimeMillis()+"".substring(5));
//		}
		Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(page-1, 20, sort);
		return userService.finaAll(user, pageable);
	}
	
	@RequestMapping("/searchUser")
	public String chooseCondition(){
		return "usersearch";
	}
	
	@RequestMapping(value="/addUser", method=RequestMethod.GET)
	public String newUserInfo(Model model){
		model.addAttribute("userForm", new UserForm());
		return "userInfo";
	}
	
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	public String addNewUser(@Valid UserForm userForm, BindingResult bindingResult,Model model) throws Exception{
        if (bindingResult.hasErrors()) {
            return "userInfo";
        }
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
		if(StringUtils.isEmpty(user.getId())){
			user.setId((System.currentTimeMillis()+"").substring(3));
		}
        userService.save(user);
		return "addsuccess";
	}
	
	
	@RequestMapping(value="/editUser/{id}", method=RequestMethod.GET)
	public String editUserInfo(@PathVariable("id") String id,Model model) throws Exception{
		User user = userService.findOne(id);
		UserForm userForm = new UserForm();
		BeanUtils.copyProperties(user, userForm);
		model.addAttribute("userForm", userForm);
		return "userInfo";
	}

	@RequestMapping(value="/editUser/{id}", method=RequestMethod.POST)
	public String updateOldUser(@PathVariable("id") String id,@ModelAttribute UserForm userForm, Model model) throws Exception{
		User user = userService.findOne(id);
		BeanUtils.copyProperties(user, userForm);
		userService.save(user);
		return "addsuccess";
	}
	
	@RequestMapping(value="/deleteUsers", method=RequestMethod.GET)
	public @ResponseBody CustomResponse deleteUsers(@RequestParam("ids") String ids) throws Exception{
		String[] deleteIds = ids.split(",");
		userService.delete(deleteIds);
		return new CustomResponse("200","删除成功！");
	}
}
