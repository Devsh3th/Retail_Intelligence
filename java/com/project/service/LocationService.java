package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dao.LocationDAO;
import com.project.vo.LocationVO;


@Service
public class LocationService {
		@Autowired LocationDAO locationDAO;
		@Transactional
		public void insertLocation(LocationVO locationVO){
			locationDAO.insertLocation(locationVO);
		}
		
		@Transactional
		public List viewLocation(){
			List ls=locationDAO.viewLocation();
			return ls;
		}
		
		@Transactional
		public List deleteLocation(LocationVO locationVO){
			List ls = locationDAO.deleteLocation(locationVO);
			return ls;
		}
		
		@Transactional
		public List editLocation(LocationVO locationVO){
			List ls = locationDAO.editLocation(locationVO);
			return ls;
		}
		
		@Transactional
		public void updateLocation(LocationVO locationVO){
			locationDAO.updateLocation(locationVO);
		}
}
