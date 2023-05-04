package com.Aashwik.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Aashwik.Entity.EnquiryStatus;
import com.Aashwik.Entity.StudentEnquiries;
import com.Aashwik.Entity.UserDetails;

public interface EnquiryStatusRepository extends JpaRepository<EnquiryStatus, Integer> {


}
