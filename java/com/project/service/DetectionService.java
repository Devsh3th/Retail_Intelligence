package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dao.DetectionDAO;

@Service
public class DetectionService {

	@Autowired
	DetectionDAO detectionDAO;
	
	@Transactional
	public List viewVisitorDetection(){
		List ls=detectionDAO.viewVisitorDetection();
		return ls;
	}
	
	@Transactional
	public List viewQueueDetection(){
		List ls=detectionDAO.viewQueueDetection();
		return ls;
	}
}
