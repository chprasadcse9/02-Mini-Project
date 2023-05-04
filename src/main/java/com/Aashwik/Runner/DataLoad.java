package com.Aashwik.Runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.Aashwik.Entity.Courses;
import com.Aashwik.Entity.EnquiryStatus;
import com.Aashwik.Repository.CoursesRepository;
import com.Aashwik.Repository.EnquiryStatusRepository;

@Component
public abstract class DataLoad implements ApplicationRunner {
	@Autowired
	private CoursesRepository coursesRepository;
	
	@Autowired
	private EnquiryStatusRepository enqRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		coursesRepository.deleteAll();
		
		Courses c1 =  new Courses();
		Courses c2 =  new Courses();
		Courses c3 =  new Courses();
		c1.setCourseName("Java Fullstack");
		c2.setCourseName("DevOps");
		c3.setCourseName("AWS");
		
		coursesRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		enqRepository.deleteAll();
		EnquiryStatus s1 =  new EnquiryStatus();
		EnquiryStatus s2 =  new EnquiryStatus();
		EnquiryStatus s3 =  new EnquiryStatus();
		s1.setStatusName("New");
		s2.setStatusName("Enrolled");
		s3.setStatusName("Lost");
	
		enqRepository.saveAll(Arrays.asList(s1,s2,s3));
		}	

		
}
