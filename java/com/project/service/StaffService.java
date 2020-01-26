package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dao.StaffDAO;
import com.project.vo.StaffVO;

@Service
public class StaffService {

	@Autowired StaffDAO staffDAO;

	@Transactional
	public void insertStaff(StaffVO staffVO) {
		// TODO Auto-generated method stub
		staffDAO.insertStaff(staffVO);
	}
	
	@Transactional
	public List viewStaff(){
		List ls=staffDAO.viewStaff();
		return ls;
	}
	
	@Transactional
	public void deleteStaff(StaffVO staffVO){
		staffDAO.deleteStaff(staffVO);
	}
	
	@Transactional
	public List editStaff(StaffVO staffVO){
		List ls=staffDAO.editStaff(staffVO);
		return ls;
	}
	
	@Transactional
	public void updateStaff(StaffVO staffVO){
		staffDAO.updateStaff(staffVO);
	}
}
