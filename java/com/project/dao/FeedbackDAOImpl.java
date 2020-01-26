package com.project.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.FeedbackVO;
@Repository
public class FeedbackDAOImpl implements FeedbackDAO {
	
	@Autowired SessionFactory sessionFactory;

	@Override
	public void insertFeedback(FeedbackVO feedbackVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.save(feedbackVO);
	}

	@Override
	public List viewFeedback() {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from FeedbackVO");
		List ls=q.list();
		return ls;
	}

}
