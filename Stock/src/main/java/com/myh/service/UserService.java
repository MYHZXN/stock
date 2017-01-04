package com.myh.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.myh.domain.Good;
import com.myh.domain.JGPage;
import com.myh.domain.User;
import com.myh.exception.CustomException;
import com.myh.repository.UserRepository;

@Service
public class UserService implements BaseService<User, String> {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	@Override
	public void save(User user) throws Exception {
		try{
			userRepository.save(user);
		}catch(Exception e){
			throw new CustomException("userId:"+user.getId()+"插入失败！why:"+e.getMessage());
		}
	}

	@Transactional
	@Override
	public void edit(User user) throws Exception {
		try{
			userRepository.save(user);
		}catch(Exception e){
			throw new CustomException("userId:"+user.getId()+"更新失败！why:"+e.getMessage());
		}
	}

	@Transactional
	@Override
	public void delete(String[] ids) throws Exception {
		for(String userId : ids){
			try{
				userRepository.delete(userId);
			}catch(Exception e){
				throw new CustomException("userId:"+userId+"删除失败！why:"+e.getMessage());
			}
		}	
	}

	@Override
	public User findOne(String id) throws Exception {
		User user = userRepository.findOne(id);
		if(user == null){
			throw new CustomException("查询不到此用户！");
		}
		return user;
	}

	@Override
	public JGPage<User> finaAll(final User user, Pageable pageable) throws Exception {
		Page<User> users = userRepository.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				if(user != null){
					List<Predicate> andPredicates = new ArrayList<Predicate>(); 
					
					Predicate idLike = null;
					if(!StringUtils.isEmpty(user.getId())){
						idLike = cb.like(root.<String> get("id"), "%"+user.getId()+"%");
						andPredicates.add(idLike);
					}
					Predicate nameLike = null;
					if(!StringUtils.isEmpty(user.getName())){
						nameLike = cb.like(root.<String> get("name"), "%"+user.getName()+"%");
						andPredicates.add(nameLike);
					}
					Predicate roleEqual = null;
					if(!StringUtils.isEmpty(user.getRole())){
						roleEqual = cb.like(root.<String> get("role"), "%"+user.getRole()+"%");
						andPredicates.add(roleEqual);
					}
					Predicate p = cb.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
					query.where(p);
				}
				return null;
			}
		}, pageable);
		JGPage<User> usersPage = new JGPage<User>(users.getTotalElements(), users.getNumber()+1, users.getTotalPages(), users.getContent()); 
		return usersPage;
	}

	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public User findOne(final User user) throws Exception{
		User u = userRepository.findOne(new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				if(user != null){
					List<Predicate> andPredicates = new ArrayList<Predicate>(); 
					
					Predicate userNameEq = null;
					if(!StringUtils.isEmpty(user.getName())){
						userNameEq = cb.equal(root.<String> get("name"), user.getName());
						andPredicates.add(userNameEq);
					}
					Predicate passWordEq = null;
					if(!StringUtils.isEmpty(user.getPassword())){
						passWordEq = cb.equal(root.<String> get("password"), user.getPassword());
						andPredicates.add(passWordEq);
					}
					Predicate p = cb.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
					query.where(p);
				}
				return null;
			}
		});
		return u;
	}
}
