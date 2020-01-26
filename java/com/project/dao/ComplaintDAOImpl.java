package com.project.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.ComplaintVO;

@Repository
public class ComplaintDAOImpl implements ComplaintDAO{

	@Autowired SessionFactory sessionFactory;

	@Override
	public void insertComplaint(ComplaintVO complaintVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.save(complaintVO);
	}

	@Override
	public List viewComplaint() {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from ComplaintVO");
		List ls=q.list();
		return ls;
	}

	@Override
	public List replyComplaint(ComplaintVO complaintVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from ComplaintVO where complaintID='"+complaintVO.getComplaintID()+"' ");
		List ls=q.list();
		return ls;
	}

	@Override
	public void updateComplaint(ComplaintVO complaintVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.update(complaintVO);
	}

	@Override
	public List viewReply(ComplaintVO complaintVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from ComplaintVO where loginVO_loginID = '"+complaintVO.getLoginVO().getLoginID()+"'");
		List ls=q.list();
		return ls;
	}
	
	
}
