package com.bookbox.models;

import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="tbl_jobs", schema="bookbox")
public class Job {
private int jobId;
private String name;
private String frequency;
private String status;
private Date previousRun;
private Date nextRun;
private Date lastModified;
private String modifiedBy;

@Id
@GeneratedValue
@Column(name="jobid")
public int getJobId() {
	return jobId;
}
public void setJobId(int jobId) {
	this.jobId = jobId;
}
@Column(name="name")
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
@Column(name="frequency")
public String getFrequency() {
	return frequency;
}
public void setFrequency(String frequency) {
	this.frequency = frequency;
}
@Column(name="status")
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
@Temporal(TemporalType.TIMESTAMP)
public Date getPreviousRun() {
	return previousRun;
}
public void setPreviousRun(Date previousRun) {
	this.previousRun = previousRun;
}
@Temporal(TemporalType.TIMESTAMP)
public Date getNextRun() {
	return nextRun;
}
public void setNextRun(Date nextRun) {
	this.nextRun = nextRun;
}
@Temporal(TemporalType.TIMESTAMP)
public Date getLastModified() {
	return lastModified;
}
public void setLastModified(Date lastModified) {
	this.lastModified = lastModified;
}
@Column(name="modifiedby")
public String getModifiedBy() {
	return modifiedBy;
}
public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}


}
