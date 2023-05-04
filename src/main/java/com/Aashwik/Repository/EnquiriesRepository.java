package com.Aashwik.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.Aashwik.Entity.StudentEnquiries;

public interface EnquiriesRepository extends JpaRepository<StudentEnquiries, Integer> {


}
