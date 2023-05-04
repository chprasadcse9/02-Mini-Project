package com.Aashwik.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Aashwik.Entity.StudentEnquiries;
import com.Aashwik.FormBinding.DashboardResponse;
import com.Aashwik.FormBinding.EnquiryForm;
import com.Aashwik.FormBinding.EnquirySearchForm;
import com.Aashwik.Repository.EnquiriesRepository;
import com.Aashwik.Service.EnquiriesService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiriesController 
{
	@Autowired
	private HttpSession session;
	
	@Autowired
	private EnquiriesService enqService;
	
	@Autowired
	private EnquiriesRepository studentEnqRepo;
	
	@GetMapping("/logout")
	public String logout() {
		
		session.invalidate();
		
		return "index";
	}
	

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		
		Integer userId =(Integer) session.getAttribute("userId");
		
		DashboardResponse dashboardData = enqService.getDashboardData(userId);
		
		model.addAttribute("dashboardData", dashboardData);
		
		return "dashboard";
	}
	
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		
		initForm(model);
		
		return "add-enquiry";
	}


	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj,Model model) {
		System.out.println(formObj);
		
      Boolean status = enqService.saveEnquiry(formObj);
		
      if (status) {
		model.addAttribute("succMsg", "Enquiry Added");	
		}else {
			model.addAttribute("errMsg", "Problem Occured");
		}
      
      
		return "add-enquiry";
	}
	
	
	
	@GetMapping("/enquires")
	public String viewEnquiriesPage(Model model) {
		initForm(model);
		 List<StudentEnquiries> enquires = enqService.getEnquiry();
		
		model.addAttribute("enquiries", enquires);
			
		return "view-enquiries";
	}


	private void initForm(Model model) {
		//get courses for the drop down
		List<String> courseNames = enqService.getCourseNames();
		//get enq status for drop down
		List<String> enqStatus = enqService.getEnqStatus();
		//create binding class object
		EnquiryForm formObj=new EnquiryForm();
		//set data in model object
		model.addAttribute("courseName",courseNames );
		model.addAttribute("StatusNames",enqStatus );
		model.addAttribute("formObj",formObj );
	}

	@GetMapping("/filter-enquiries")
	public String getFilteredEnquiries(@RequestParam String cname, @RequestParam String status, @RequestParam String mode, Model model) 
	{
		EnquirySearchForm criteria=	new EnquirySearchForm();
		criteria.setCourseName(cname);
		criteria.setEnquiryStatus(status);
		criteria.setClassMode(mode);
	
		System.out.println(criteria);
	
		Integer userId =(Integer) session.getAttribute("userId");
	
		List<StudentEnquiries> filteredEnqs = enqService.getFilteredEnquiries(criteria, userId);
		
		model.addAttribute("enquiries", filteredEnqs);
	
		return "filter-enquiries";
	}
	
	@GetMapping("/edit")
	public String editEnq(@RequestParam("enquiryId") Integer enquiryId,Model model) {
		
		Optional<StudentEnquiries> findById = studentEnqRepo.findById(enquiryId);
		
		if(findById.isPresent()) {
			
			StudentEnquiries studentEnqEntity = findById.get();
			
			
			
			//get courses for the drop down
			List<String> courseNames = enqService.getCourseNames();
			//get enq status for drop down
			List<String> enqStatus = enqService.getEnqStatus();
			//create binding class object
			EnquiryForm formObj=new EnquiryForm();
			
			BeanUtils.copyProperties(studentEnqEntity, formObj);
			
			//set data in model object
			model.addAttribute("courseName",courseNames );
			model.addAttribute("StatusNames",enqStatus );
			model.addAttribute("formObj",formObj );
			
		}
		
		return"add-enquiry";
	}
	
}