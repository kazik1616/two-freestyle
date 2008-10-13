package com.kkazmierczyk.freestyle;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/* @version $Id: LoggedRequest.java 7 2008-05-27 20:52:33Z kazik $ */
@Entity
@Table
public class LoggedRequest {

	@Id
	@GeneratedValue
	private long id;

	@Column
	private Date date = new Date();

	@Column
	private String path;

	@Column
	private String agent;

	public Date getDate() {
		return date;
	}

	public String getPath() {
		return path;
	}

	public String getAgent() {
		return agent;
	}

	public LoggedRequest(String agent, String path) {
		this.agent = agent;
		this.path = path;
	}

	public LoggedRequest() {
	}

}
