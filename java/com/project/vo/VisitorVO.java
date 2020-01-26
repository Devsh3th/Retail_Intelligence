package com.project.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="visitor_table")
public class VisitorVO {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="visitorId")
	private int visitorId;
	
	@Column(name="up")
	private String up;
	
	@Column(name="down")
	private String down;
	
	@Column(name="visitor_date")
	private String visitor_date;
	
	@Column(name="visitor_time")
	private String visitor_time;

	public int getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(int visitorId) {
		this.visitorId = visitorId;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public String getDown() {
		return down;
	}

	public void setDown(String down) {
		this.down = down;
	}

	public String getVisitor_date() {
		return visitor_date;
	}

	public void setVisitor_date(String visitor_date) {
		this.visitor_date = visitor_date;
	}

	public String getVisitor_time() {
		return visitor_time;
	}

	public void setVisitor_time(String visitor_time) {
		this.visitor_time = visitor_time;
	}
}
