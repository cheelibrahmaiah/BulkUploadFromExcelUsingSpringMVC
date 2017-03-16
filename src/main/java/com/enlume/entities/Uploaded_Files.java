package com.enlume.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UPLD_FILES")
public class Uploaded_Files {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="FILE_ID")
	private int file_id;
	
	@Column(name="FILE_NAME")
	private String file_name;
	
	@Column(name="RECORDS")
	private int records;
	
	@Column(name="DATE")
	private String date;
	
	@Column(name="UPLOAD_BY")
	private String uplaodBy;

	public int getFile_id() {
		return file_id;
	}

	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}	

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUplaodBy() {
		return uplaodBy;
	}

	public void setUplaodBy(String uplaodBy) {
		this.uplaodBy = uplaodBy;
	}
	

}
