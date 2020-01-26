package com.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.dao.DatasetDAO;
import com.project.vo.DatasetVO;
import com.project.vo.LocationVO;

@Service
public class DatasetService {
	@Autowired DatasetDAO datasetDAO;
 
 	@Transactional
 	public void insertDataset(DatasetVO datasetVO){
	 datasetDAO.insertDataset(datasetVO);
 	}
 
 	@Transactional
 	public List viewDataset(){
 		List ls=datasetDAO.viewDataset();
 		return ls;
 	}
 
 	@Transactional
	public List deleteDataset(DatasetVO datasetVO){
		List ls = datasetDAO.deleteDataset(datasetVO);
		return ls;
	}
	
	@Transactional
	public List editDataset(DatasetVO datasetVO){
		List ls = datasetDAO.editDataset(datasetVO);
		return ls;
	}
	
	@Transactional
	public void updateDataset(DatasetVO datasetVO){
		datasetDAO.updateDataset(datasetVO);
	}

}
