package com.project.dao;

import java.util.List;

import com.project.vo.LoginVO;

public interface LoginDAO {
	public void insert(LoginVO loginVO);
	public void delete(LoginVO loginVO);
	public List serchByUserName(String userName);
	public List generateGraph(String a);
}
