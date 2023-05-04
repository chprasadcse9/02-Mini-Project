package com.Aashwik.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Aashwik.Entity.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Integer> {

	public UserDetails findByEmail(String email);
	public UserDetails findByEmailAndPwd(String email, String pwd);
	
}
