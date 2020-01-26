package com.project.dao;

import java.util.List;

import com.project.vo.ComplaintVO;

public interface ComplaintDAO {
	
	public void insertComplaint(ComplaintVO complaintVO);
	
	public List viewComplaint();
	
	public List replyComplaint(ComplaintVO complaintVO);
	
	public void updateComplaint(ComplaintVO complaintVO);
	
	public List viewReply(ComplaintVO complaintVO);
	
}
