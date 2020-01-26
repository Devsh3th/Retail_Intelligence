package com.project.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="detection_table")


public class QueueDetectionVO {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="detectionId")
	private int detectionId;
	
	@Column(name="queue")
	private String queue;
	
	@Column(name="queueCount")
	private String queueCount;
	
	@Column(name="detection_date")
	private String detection_date;
	
	@Column(name="detection_time")
	private String detection_time;

	public int getDetectionId() {
		return detectionId;
	}

	public void setDetectionId(int detectionId) {
		this.detectionId = detectionId;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getQueueCount() {
		return queueCount;
	}

	public void setQueueCount(String queueCount) {
		this.queueCount = queueCount;
	}

	public String getDetection_date() {
		return detection_date;
	}

	public void setDetection_date(String detection_date) {
		this.detection_date = detection_date;
	}

	public String getDetection_time() {
		return detection_time;
	}

	public void setDetection_time(String detection_time) {
		this.detection_time = detection_time;
	}
	
}
