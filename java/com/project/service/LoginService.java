package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dao.LoginDAO;
import com.project.vo.LoginVO;

@Service
public class LoginService {
	@Autowired LoginDAO loginDAO;
	
	@Transactional
	public void insert(LoginVO loginVO){
		loginDAO.insert(loginVO);
	}
	
	@Transactional
	public void delete(LoginVO loginVO){
		loginDAO.delete(loginVO);
	}

	
	@Transactional
	public List serchByUserName(String userName) {

		List loginList = this.loginDAO.serchByUserName(userName);
		return loginList;
	}
	@Transactional
	public List generateGraph(String a){
		List ls=this.loginDAO.generateGraph(a);
		return ls;
	}
	
	
}
