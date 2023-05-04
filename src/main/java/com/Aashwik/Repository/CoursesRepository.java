package com.Aashwik.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Aashwik.Entity.Courses;

public interface CoursesRepository extends JpaRepository<Courses, Integer> {

}
