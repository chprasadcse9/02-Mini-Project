package com.Aashwik.Service;

import java.util.List;

import com.Aashwik.Entity.StudentEnquiries;
import com.Aashwik.FormBinding.DashboardResponse;
import com.Aashwik.FormBinding.EnquiryForm;
import com.Aashwik.FormBinding.EnquirySearchForm;

public interface EnquiriesService {

	public DashboardResponse getDashboardData(Integer userId);
	
	public List<String> getCourseNames();
	
	public List<String> getEnqStatus();

	Boolean saveEnquiry(EnquiryForm form);

	public List<StudentEnquiries> getEnquiry();
	
	public List<StudentEnquiries> getFilteredEnquiries(EnquirySearchForm criteria,Integer userId);

	
}
