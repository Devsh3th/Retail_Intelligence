package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.dao.ComplaintDAO;
import com.project.vo.ComplaintVO;

@Service
public class ComplaintService {
	@Autowired ComplaintDAO complaintDAO;
	
	@Transactional
	public void insertComplaint(ComplaintVO complaintVO){
		this.complaintDAO.insertComplaint(complaintVO);
	}
	
	@Transactional
	public List viewComplaint(){
		List ls=complaintDAO.viewComplaint();
 		return ls;
	}
	
	@Transactional
	public List replyComplaint(ComplaintVO complaintVO){
		List ls=this.complaintDAO.replyComplaint(complaintVO);
		return ls;
	}
	@Transactional
	public void updateComplaint(ComplaintVO complaintVO){
		this.complaintDAO.updateComplaint(complaintVO);
	}
	
	@Transactional
	public List viewReply(ComplaintVO complaintVO){
		List ls=this.complaintDAO.viewReply(complaintVO);
		return ls;
	}
}