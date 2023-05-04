package com.Aashwik.Entity;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Student_Enquiries")
public class StudentEnquiries {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer enquiryId;
	private String studentName;
	private Integer contactNo;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
	@CreationTimestamp
	private LocalDate createdDate;
	@CreationTimestamp
	private LocalDate updatedDate;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private UserDetails user;
	
	
}
