package com.Aashwik.FormBinding;

import java.util.Date;

import lombok.Data;

@Data
public class EnquiryForm {

	private String studentName;
	private String courseName;
	private Integer contactNo;
	private Date createdDate;
	private String enquiryStatus;
	
}
