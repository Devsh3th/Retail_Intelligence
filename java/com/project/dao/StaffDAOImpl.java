package com.project.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.StaffVO;

@Repository
public class StaffDAOImpl implements StaffDAO{
	
	@Autowired SessionFactory sessionFactory;

	 public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void insertStaff(StaffVO staffVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.save(staffVO);
	}

	@Override
	public List viewStaff() {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from StaffVO where status=true");
		List ls=q.list();
		return ls;
	}

	@Override
	public void deleteStaff(StaffVO staffVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.delete(staffVO);
	}

	@Override
	public List editStaff(StaffVO staffVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from StaffVO where loginVO_loginID = '"+staffVO.getLoginVO().getLoginID()+"'");
		List ls=q.list();
		return ls;
	}

	@Override
	public void updateStaff(StaffVO staffVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.update(staffVO);
	}
	
	
	 
}
