package com.project.dao;

import java.util.List;

import com.project.vo.StaffVO;

public interface StaffDAO {
	public void insertStaff(StaffVO staffVO);
	public List viewStaff();
	public void deleteStaff(StaffVO staffVO);
	public List editStaff(StaffVO staffVO);
	public void updateStaff(StaffVO staffVO);
}
