package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.BranchVO;
import com.project.vo.LocationVO;

@Repository
public class BranchDAOImpl implements BranchDAO {
	
	@Autowired SessionFactory sessionFactory;
	
	 public void setSessionFactory(SessionFactory sf) {
			this.sessionFactory = sf;
		}

	@Override
	public void insertBranch(BranchVO branchVO) {
		Session session=this.sessionFactory.getCurrentSession();
		session.save(branchVO);
	}
	
	
	@Override
	public List viewBranch() {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from BranchVO where status=true");
		List ls =q.list();
		return ls;
	}

	@Override
	public List deleteBranch(BranchVO branchVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from BranchVO where branchID='"+branchVO.getBranchID()+"' ");
		List ls=q.list();
		return ls;
	}

	@Override
	public List fetchBranch(BranchVO branchVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from BranchVO where locationVO.locationID='"+branchVO.getLocationVO().getLocationID()+"' ");
		List ls=q.list();
		return ls;
	}
	
	@Override
	public List editBranch(BranchVO branchVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from BranchVO where branchID='"+branchVO.getBranchID()+"' ");
		List ls=q.list();
		return ls;
	}

	@Override
	public void updateBranch(BranchVO branchVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.update(branchVO);
	}
}
