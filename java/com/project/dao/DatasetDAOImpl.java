package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.vo.DatasetVO;
import com.project.vo.LocationVO;

@Repository
public class DatasetDAOImpl implements DatasetDAO {
	@Autowired SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory=sf;
	}

	@Override
	public void insertDataset(DatasetVO datasetVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.save(datasetVO);
	}

	@Override
	public List viewDataset() {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from DatasetVO where status=true");
		List ls=q.list();
		return ls;
	}
	
	@Override
	public List deleteDataset(DatasetVO datasetVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from DatasetVO where datasetID='"+datasetVO.getDatasetID()+"' ");
		List ls=q.list();
		return ls;
	}

	@Override
	public List editDataset(DatasetVO datasetVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		Query q=session.createQuery("from DatasetVO where datasetID='"+datasetVO.getDatasetID()+"' ");
		List ls=q.list();
		return ls;
	}
	@Override
	public void updateDataset(DatasetVO datasetVO) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		session.update(datasetVO);
	}
	
}
