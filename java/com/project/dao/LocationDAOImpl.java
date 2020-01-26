package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.LocationVO;

@Repository
public class LocationDAOImpl implements LocationDAO {
	
	@Autowired SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void insertLocation(LocationVO locationVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.save(locationVO);
	}

	@Override
	public List viewLocation() {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from LocationVO where status=true");
		List ls=q.list();
		return ls;
	}

	@Override
	public List deleteLocation(LocationVO locationVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from LocationVO where locationID='"+locationVO.getLocationID()+"' ");
		List ls=q.list();
		return ls;
	}

	@Override
	public List editLocation(LocationVO locationVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from LocationVO where locationID='"+locationVO.getLocationID()+"' ");
		List ls=q.list();
		return ls;
	}

	@Override
	public void updateLocation(LocationVO locationVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.update(locationVO);
	}
}
