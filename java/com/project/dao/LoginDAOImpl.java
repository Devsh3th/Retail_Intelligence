package com.project.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.LoginVO;
@Repository
public class LoginDAOImpl implements LoginDAO {

	@Autowired SessionFactory sessionFactory;
	
	 public void setSessionFactory(SessionFactory sf) {
			this.sessionFactory = sf;
		}
	 
	 
	@Override
	public void insert(LoginVO loginVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(loginVO);
	}


	@Override
	public void delete(LoginVO loginVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.delete(loginVO);
	}
	
	@Override
	public List serchByUserName(String userName)
	{
		Session session=this.sessionFactory.getCurrentSession();
		Query q = session.createQuery("from LoginVO where username ='" +userName+"'" );
		//q.setParameter("usernameParam", userName);
		List loginList = q.list();
		return loginList;
	
	}


	@Override
	public List generateGraph(String a) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q = session.createQuery("from VisitorVO" );
		List ls = q.list();
		return ls; 
	}
}
