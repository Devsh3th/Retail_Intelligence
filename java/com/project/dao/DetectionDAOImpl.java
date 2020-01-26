package com.project.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DetectionDAOImpl implements DetectionDAO {
	
	@Autowired SessionFactory sessionFactory;
	
	 public void setSessionFactory(SessionFactory sf) {
			this.sessionFactory = sf;
		}

	@Override
	public List viewVisitorDetection() {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from VisitorVO");
		List ls=q.list();
		return ls;
		
	}

	@Override
	public List viewQueueDetection() {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from QueueDetectionVO");
		List ls=q.list();
		return ls;
	}

}
