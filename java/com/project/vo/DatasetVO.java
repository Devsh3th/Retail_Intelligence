package com.project.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DatasetTable")
public class DatasetVO {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="datasetID")
	private int datasetID;
	
	@Column(name="datasetDescription")
	private String datasetDescription;
	
	@Column(name="datasetFileName")
	private String datasetFileName;
	
	@Column(name="datasetFilePath")
	private String datasetFilePath;
	
	public String getDatasetFileName() {
		return datasetFileName;
	}

	public void setDatasetFileName(String datasetFileName) {
		this.datasetFileName = datasetFileName;
	}

	public String getDatasetFilePath() {
		return datasetFilePath;
	}

	public void setDatasetFilePath(String datasetFilePath) {
		this.datasetFilePath = datasetFilePath;
	}

	private boolean status=true;

	public int getDatasetID() {
		return datasetID;
	}

	public void setDatasetID(int datasetID) {
		this.datasetID = datasetID;
	}

	public String getDatasetDescription() {
		return datasetDescription;
	}

	public void setDatasetDescription(String datasetDescription) {
		this.datasetDescription = datasetDescription;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
