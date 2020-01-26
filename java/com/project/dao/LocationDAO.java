package com.project.dao;

import java.util.List;

import com.project.vo.LocationVO;



public interface LocationDAO {
	public void insertLocation(LocationVO locationVO);
	public List viewLocation();
	public List deleteLocation(LocationVO locationVO);
	public List editLocation(LocationVO locationVO);
	public void updateLocation(LocationVO locationVO);
}
