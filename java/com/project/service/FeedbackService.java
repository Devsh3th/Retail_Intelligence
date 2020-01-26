package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dao.FeedbackDAO;
import com.project.vo.FeedbackVO;

@Service
public class FeedbackService {
	
	@Autowired FeedbackDAO feedbackDAO;
	
	@Transactional
	public void insertFeedback(FeedbackVO feedbackVO){
		this.feedbackDAO.insertFeedback(feedbackVO);
	}
	
	@Transactional
	public List viewFeedback(){
		List ls=this.feedbackDAO.viewFeedback();
		return ls;
	}
}
