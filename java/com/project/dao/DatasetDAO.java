package com.project.dao;

import java.util.List;

import com.project.vo.DatasetVO;

public interface DatasetDAO {
	public void insertDataset(DatasetVO datasetVO);
	public List viewDataset();
	public List deleteDataset(DatasetVO datasetVO);
	public List editDataset(DatasetVO datasetVO);
	public void updateDataset(DatasetVO datasetVO);
}
