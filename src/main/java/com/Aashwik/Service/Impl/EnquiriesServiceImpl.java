package com.Aashwik.Service.Impl;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Aashwik.Entity.Courses;
import com.Aashwik.Entity.EnquiryStatus;
import com.Aashwik.Entity.StudentEnquiries;
import com.Aashwik.Entity.UserDetails;
import com.Aashwik.FormBinding.DashboardResponse;
import com.Aashwik.FormBinding.EnquiryForm;
import com.Aashwik.FormBinding.EnquirySearchForm;
import com.Aashwik.Repository.CoursesRepository;
import com.Aashwik.Repository.EnquiriesRepository;
import com.Aashwik.Repository.EnquiryStatusRepository;
import com.Aashwik.Repository.UserRepository;
import com.Aashwik.Service.EnquiriesService;

import jakarta.servlet.http.HttpSession;



@Service
public class EnquiriesServiceImpl implements EnquiriesService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CoursesRepository courseRepo;
	
	@Autowired
	private EnquiryStatusRepository enqStatusRepo;
	
	@Autowired
	private EnquiriesRepository enqRepo;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public List<String> getCourseNames() {
		List<Courses> courses = courseRepo.findAll();
		
		List<String> courseName=new ArrayList<>();
		
		for (Courses courseEntity : courses) {
			courseName.add(courseEntity.getCourseName());
		}
		return courseName;
	}

	@Override
	public List<String> getEnqStatus() {
		
		List<EnquiryStatus> enqStatus = enqStatusRepo.findAll();
		List<String> enqStatusName=new ArrayList<>();
		
		for (EnquiryStatus entity:enqStatus) {
			enqStatusName.add(entity.getStatusName());
		}
		
		return enqStatusName;
	}

	
	
	
	@Override
	public DashboardResponse getDashboardData(Integer userId) {
		
		DashboardResponse response=new DashboardResponse();
		
		Optional<UserDetails> userOptional = userRepo.findById(userId);
		
		if (userOptional.isPresent()) {
			UserDetails user = userOptional.get();
			
			List<StudentEnquiries> enquiries = user.getEnquiries();
			
			Integer totalCnt = enquiries.size();
			
			Integer enroledCnt = enquiries.stream()
					.filter(e->e.getEnquiryStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();
			
			Integer LostCnt = enquiries.stream()
					.filter(e->e.getEnquiryStatus().equals("Lost"))
					.collect(Collectors.toList()).size();
			
			response.setTotalCount(totalCnt);
			response.setEnrolledCount(enroledCnt);
			response.setLostCount(LostCnt);	
			
		}
		
		return response;
	}
	
	@Override
	public Boolean saveEnquiry(EnquiryForm form) {
		
		StudentEnquiries enqEntity=new StudentEnquiries(); 
		 
		BeanUtils.copyProperties(form, enqEntity);
		
		Integer userId=(Integer)session.getAttribute("userId");
		Optional<UserDetails> findById = userRepo.findById(userId);
		
		UserDetails UserDetails = findById.get();
		enqEntity.setUser(UserDetails);
		
		enqRepo.save(enqEntity);
		
		return true;
	}

	@Override
	public List<StudentEnquiries> getEnquiry() {
		// TODO Auto-generated method stub
		Integer userId=(Integer)session.getAttribute("userId");
		
		Optional<UserDetails> findById = userRepo.findById(userId);
			
		UserDetails UserDetails = findById.get();
		
		List<StudentEnquiries> enquiries = UserDetails.getEnquiries();
		
		return enquiries;
	}
		

	@Override
	public List<StudentEnquiries> getFilteredEnquiries(EnquirySearchForm criteria, Integer userId) {
		// TODO Auto-generated method stub
		Optional<UserDetails> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
		
		UserDetails UserDetails = findById.get();
		
		List<StudentEnquiries> enquiries = UserDetails.getEnquiries();
		
		//filter Logic
		
		if (null !=criteria.getCourseName() & !"".equals(criteria.getCourseName())) {
			enquiries = enquiries.stream()
			.filter(e -> e.getCourseName().equals(criteria.getCourseName()))
			.collect(Collectors.toList());
		}
		
		if (null !=criteria.getEnquiryStatus() & !"".equals(criteria.getEnquiryStatus())) {
			enquiries = enquiries.stream()
			.filter(e -> e.getEnquiryStatus().equals(criteria.getEnquiryStatus()))
			.collect(Collectors.toList());
		}
		
		if (null !=criteria.getClassMode() & !"".equals(criteria.getClassMode() )) {
			enquiries = enquiries.stream()
			.filter(e -> e.getClassMode() .equals(criteria.getClassMode() ))
			.collect(Collectors.toList());
		}
	
		return enquiries;
		}
		return null;
	}
		
}
